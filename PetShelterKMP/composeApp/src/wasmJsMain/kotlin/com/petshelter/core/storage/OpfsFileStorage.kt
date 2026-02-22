@file:Suppress("TooManyFunctions")
@file:OptIn(kotlin.js.ExperimentalWasmJsInterop::class)

package com.petshelter.core.storage

import kotlinx.coroutines.await
import kotlin.js.Promise

class OpfsFileStorage : FileStorage {
    private val cache = mutableMapOf<String, ByteArray>()

    override suspend fun save(
        path: String,
        data: ByteArray,
    ) {
        cache[path] = data
        writeToOpfs(path, data)
    }

    override suspend fun load(path: String): ByteArray? {
        cache[path]?.let { return it }
        val data = readFromOpfs(path) ?: return null
        cache[path] = data
        return data
    }

    override suspend fun delete(path: String) {
        cache.remove(path)
        deleteFromOpfs(path)
    }

    override suspend fun exists(path: String): Boolean {
        if (cache.containsKey(path)) return true
        return existsInOpfs(path)
    }

    private suspend fun writeToOpfs(
        path: String,
        data: ByteArray,
    ) {
        if (!isOpfsAvailable()) return
        try {
            val root = getOpfsRoot()
            val dirHandle = ensureDirectoryExists(root, parentPath(path))
            val fileHandle = createFileInDir(dirHandle, fileName(path))
            val writable = openWritable(fileHandle)
            writeData(writable, data)
            closeStream(writable)
        } catch (_: Throwable) {
            // OPFS unavailable; data remains in cache
        }
    }

    private suspend fun readFromOpfs(path: String): ByteArray? {
        if (!isOpfsAvailable()) return null
        return try {
            val root = getOpfsRoot()
            val dirHandle = navigateToDirectory(root, parentPath(path)) ?: return null
            val fileHandle = getExistingFile(dirHandle, fileName(path)) ?: return null
            val file = fileFromHandle(fileHandle)
            val buffer = readArrayBuffer(file)
            convertToByteArray(buffer)
        } catch (_: Throwable) {
            null
        }
    }

    private suspend fun deleteFromOpfs(path: String) {
        if (!isOpfsAvailable()) return
        try {
            val root = getOpfsRoot()
            val dirHandle = navigateToDirectory(root, parentPath(path)) ?: return
            removeFile(dirHandle, fileName(path))
        } catch (_: Throwable) {
            // Ignore
        }
    }

    private suspend fun existsInOpfs(path: String): Boolean {
        if (!isOpfsAvailable()) return false
        return try {
            val root = getOpfsRoot()
            val dirHandle = navigateToDirectory(root, parentPath(path)) ?: return false
            getExistingFile(dirHandle, fileName(path)) != null
        } catch (_: Throwable) {
            false
        }
    }

    private suspend fun ensureDirectoryExists(
        root: JsAny,
        path: String,
    ): JsAny {
        if (path.isEmpty()) return root
        var current = root
        for (segment in path.split("/")) {
            if (segment.isEmpty()) continue
            @Suppress("UNCHECKED_CAST")
            current = (getDirectoryHandleCreate(current, segment) as Promise<JsAny>).await()
        }
        return current
    }

    private suspend fun navigateToDirectory(
        root: JsAny,
        path: String,
    ): JsAny? {
        if (path.isEmpty()) return root
        var current = root
        try {
            for (segment in path.split("/")) {
                if (segment.isEmpty()) continue
                @Suppress("UNCHECKED_CAST")
                current = (getDirectoryHandleExisting(current, segment) as Promise<JsAny>).await()
            }
        } catch (_: Throwable) {
            return null
        }
        return current
    }

    private suspend fun createFileInDir(
        dirHandle: JsAny,
        name: String,
    ): JsAny {
        @Suppress("UNCHECKED_CAST")
        return (getFileHandleCreate(dirHandle, name) as Promise<JsAny>).await()
    }

    private suspend fun getExistingFile(
        dirHandle: JsAny,
        name: String,
    ): JsAny? =
        try {
            @Suppress("UNCHECKED_CAST")
            (getFileHandleExisting(dirHandle, name) as Promise<JsAny>).await()
        } catch (_: Throwable) {
            null
        }

    private suspend fun openWritable(fileHandle: JsAny): JsAny {
        @Suppress("UNCHECKED_CAST")
        return (createWritableStream(fileHandle) as Promise<JsAny>).await()
    }

    private suspend fun writeData(
        writable: JsAny,
        data: ByteArray,
    ) {
        val uint8Array = byteArrayToUint8Array(data)

        @Suppress("UNCHECKED_CAST")
        val promise: Promise<JsAny> = writeToStream(writable, uint8Array) as Promise<JsAny>
        promise.await<JsAny>()
    }

    private suspend fun closeStream(writable: JsAny) {
        @Suppress("UNCHECKED_CAST")
        val promise: Promise<JsAny> = closeWritableStream(writable) as Promise<JsAny>
        promise.await<JsAny>()
    }

    private suspend fun fileFromHandle(fileHandle: JsAny): JsAny {
        @Suppress("UNCHECKED_CAST")
        return (getFileFromHandle(fileHandle) as Promise<JsAny>).await()
    }

    private suspend fun readArrayBuffer(file: JsAny): JsAny {
        @Suppress("UNCHECKED_CAST")
        return (fileArrayBuffer(file) as Promise<JsAny>).await()
    }

    private fun convertToByteArray(buffer: JsAny): ByteArray {
        val int8Array = toInt8Array(buffer)
        val length = int8ArrayLength(int8Array)
        return ByteArray(length) { int8ArrayGet(int8Array, it) }
    }

    private suspend fun removeFile(
        dirHandle: JsAny,
        name: String,
    ) {
        try {
            @Suppress("UNCHECKED_CAST")
            (removeEntryFromDir(dirHandle, name) as Promise<JsAny>).await()
        } catch (_: Throwable) {
            // Ignore
        }
    }

    private fun parentPath(path: String): String {
        val lastSlash = path.lastIndexOf('/')
        return if (lastSlash > 0) path.substring(0, lastSlash) else ""
    }

    private fun fileName(path: String): String {
        val lastSlash = path.lastIndexOf('/')
        return if (lastSlash >= 0) path.substring(lastSlash + 1) else path
    }
}

@Suppress("MaxLineLength")
@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("() => typeof navigator !== 'undefined' && navigator.storage && typeof navigator.storage.getDirectory === 'function'")
private external fun isOpfsAvailable(): Boolean

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("() => navigator.storage.getDirectory()")
private external fun getOpfsRootJs(): JsAny

private suspend fun getOpfsRoot(): JsAny {
    @Suppress("UNCHECKED_CAST")
    return (getOpfsRootJs() as Promise<JsAny>).await()
}

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("(dir, name) => dir.getDirectoryHandle(name, {create: true})")
private external fun getDirectoryHandleCreate(
    dir: JsAny,
    name: String,
): JsAny

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("(dir, name) => dir.getDirectoryHandle(name)")
private external fun getDirectoryHandleExisting(
    dir: JsAny,
    name: String,
): JsAny

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("(dir, name) => dir.getFileHandle(name, {create: true})")
private external fun getFileHandleCreate(
    dir: JsAny,
    name: String,
): JsAny

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("(dir, name) => dir.getFileHandle(name)")
private external fun getFileHandleExisting(
    dir: JsAny,
    name: String,
): JsAny

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("(handle) => handle.createWritable()")
private external fun createWritableStream(handle: JsAny): JsAny

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("(writable, data) => writable.write(data)")
private external fun writeToStream(
    writable: JsAny,
    data: JsAny,
): JsAny

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("(writable) => writable.close()")
private external fun closeWritableStream(writable: JsAny): JsAny

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("(handle) => handle.getFile()")
private external fun getFileFromHandle(handle: JsAny): JsAny

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("(file) => file.arrayBuffer()")
private external fun fileArrayBuffer(file: JsAny): JsAny

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("(buffer) => new Int8Array(buffer)")
private external fun toInt8Array(buffer: JsAny): JsAny

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("(arr) => arr.length")
private external fun int8ArrayLength(arr: JsAny): Int

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("(arr, i) => arr[i]")
private external fun int8ArrayGet(
    arr: JsAny,
    index: Int,
): Byte

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("(dir, name) => dir.removeEntry(name)")
private external fun removeEntryFromDir(
    dir: JsAny,
    name: String,
): JsAny

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("(data) => new Uint8Array(data)")
private external fun createUint8ArrayOfSize(size: Int): JsAny

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
@JsFun("(arr, i, v) => { arr[i] = v & 0xFF; }")
private external fun setUint8ArrayElement(
    arr: JsAny,
    index: Int,
    value: Byte,
)

private fun byteArrayToUint8Array(data: ByteArray): JsAny {
    val uint8Array = createUint8ArrayOfSize(data.size)
    for (i in data.indices) {
        setUint8ArrayElement(uint8Array, i, data[i])
    }
    return uint8Array
}
