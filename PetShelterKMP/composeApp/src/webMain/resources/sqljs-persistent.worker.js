"use strict";

importScripts("/sql-wasm.js");

var DB_NAME = "petshelter-sqldelight";
var DB_STORE = "database";
var DB_KEY = "db";

var db = null;
var idb = null;

function openIndexedDB() {
  return new Promise(function (resolve, reject) {
    var request = indexedDB.open(DB_NAME, 1);
    request.onupgradeneeded = function () {
      request.result.createObjectStore(DB_STORE);
    };
    request.onsuccess = function () { resolve(request.result); };
    request.onerror = function () { reject(request.error); };
  });
}

function loadFromIndexedDB() {
  return new Promise(function (resolve, reject) {
    var tx = idb.transaction(DB_STORE, "readonly");
    var store = tx.objectStore(DB_STORE);
    var request = store.get(DB_KEY);
    request.onsuccess = function () { resolve(request.result || null); };
    request.onerror = function () { reject(request.error); };
  });
}

function saveToIndexedDB(data) {
  return new Promise(function (resolve, reject) {
    var tx = idb.transaction(DB_STORE, "readwrite");
    var store = tx.objectStore(DB_STORE);
    var request = store.put(data, DB_KEY);
    request.onsuccess = function () { resolve(); };
    request.onerror = function () { reject(request.error); };
  });
}

function persistDatabase() {
  if (db && idb) {
    var data = db.export();
    saveToIndexedDB(data.buffer).catch(function (err) {
      console.error("Failed to persist database:", err);
    });
  }
}

function createDatabase() {
  return initSqlJs({ locateFile: function (file) { return "/" + file; } })
    .then(function (SQL) {
      return openIndexedDB().then(function (database) {
        idb = database;
        return loadFromIndexedDB();
      }).then(function (saved) {
        if (saved) {
          db = new SQL.Database(new Uint8Array(saved));
        } else {
          db = new SQL.Database();
        }
      });
    });
}

function onModuleReady() {
  var data = this.data;

  switch (data && data.action) {
    case "exec":
      if (!data["sql"]) {
        throw new Error("exec: Missing query string");
      }
      var results = db.exec(data.sql, data.params)[0] || { values: [] };
      persistDatabase();
      return postMessage({
        id: data.id,
        results: results,
      });
    case "begin_transaction":
      return postMessage({
        id: data.id,
        results: db.exec("BEGIN TRANSACTION;"),
      });
    case "end_transaction":
      var endResults = db.exec("END TRANSACTION;");
      persistDatabase();
      return postMessage({
        id: data.id,
        results: endResults,
      });
    case "rollback_transaction":
      return postMessage({
        id: data.id,
        results: db.exec("ROLLBACK TRANSACTION;"),
      });
    default:
      throw new Error("Unsupported action: " + (data && data.action));
  }
}

function onError(err) {
  return postMessage({
    id: this.data.id,
    error: err,
  });
}

db = null;
var sqlModuleReady = createDatabase();
self.onmessage = function (event) {
  return sqlModuleReady
    .then(onModuleReady.bind(event))
    .catch(onError.bind(event));
};
