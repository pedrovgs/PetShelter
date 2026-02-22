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
            val root = getOpfsRoot() ?: return
            val dirHandle = ensureDirectoryExists(root, parentPath(path))
            val fileHandle = createFileHandle(dirHandle, fileName(path))
            val writable = createWritable(fileHandle)
            writeBytes(writable, data)
            closeWritable(writable)
        } catch (_: dynamic) {
            // OPFS unavailable or error; data is in cache
        }
    }

    @Suppress("ReturnCount")
    private suspend fun readFromOpfs(path: String): ByteArray? {
        if (!isOpfsAvailable()) return null
        return try {
            val root = getOpfsRoot() ?: return null
            val dirHandle = navigateToDirectory(root, parentPath(path)) ?: return null
            val fileHandle = getFileHandle(dirHandle, fileName(path)) ?: return null
            val file = getFile(fileHandle)
            val buffer = fileToArrayBuffer(file)
            int8ArrayToByteArray(buffer)
        } catch (_: dynamic) {
            null
        }
    }

    private suspend fun deleteFromOpfs(path: String) {
        if (!isOpfsAvailable()) return
        try {
            val root = getOpfsRoot() ?: return
            val dirHandle = navigateToDirectory(root, parentPath(path)) ?: return
            removeEntry(dirHandle, fileName(path))
        } catch (_: dynamic) {
            // Ignore removal errors
        }
    }

    private suspend fun existsInOpfs(path: String): Boolean {
        if (!isOpfsAvailable()) return false
        return try {
            val root = getOpfsRoot() ?: return false
            val dirHandle = navigateToDirectory(root, parentPath(path)) ?: return false
            getFileHandle(dirHandle, fileName(path)) != null
        } catch (_: dynamic) {
            false
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

// JS interop for OPFS API

@Suppress("MaxLineLength")
private fun isOpfsAvailable(): Boolean =
    js("typeof navigator !== 'undefined' && navigator.storage && typeof navigator.storage.getDirectory === 'function'") as Boolean

private suspend fun getOpfsRoot(): dynamic {
    val promise = js("navigator.storage.getDirectory()") as Promise<dynamic>
    return promise.await()
}

private suspend fun ensureDirectoryExists(
    root: dynamic,
    path: String,
): dynamic {
    if (path.isEmpty()) return root
    var current = root
    for (segment in path.split("/")) {
        if (segment.isEmpty()) continue
        current = (current.getDirectoryHandle(segment, js("({create: true})")) as Promise<dynamic>).await()
    }
    return current
}

private suspend fun navigateToDirectory(
    root: dynamic,
    path: String,
): dynamic? {
    if (path.isEmpty()) return root
    var current = root
    try {
        for (segment in path.split("/")) {
            if (segment.isEmpty()) continue
            current = (current.getDirectoryHandle(segment) as Promise<dynamic>).await()
        }
    } catch (_: dynamic) {
        return null
    }
    return current
}

private suspend fun createFileHandle(
    dirHandle: dynamic,
    name: String,
): dynamic = (dirHandle.getFileHandle(name, js("({create: true})")) as Promise<dynamic>).await()

private suspend fun getFileHandle(
    dirHandle: dynamic,
    name: String,
): dynamic? =
    try {
        (dirHandle.getFileHandle(name) as Promise<dynamic>).await()
    } catch (_: dynamic) {
        null
    }

@Suppress("MaxLineLength")
private suspend fun createWritable(fileHandle: dynamic): dynamic = (fileHandle.createWritable() as Promise<dynamic>).await()

private suspend fun writeBytes(
    writable: dynamic,
    data: ByteArray,
) {
    val jsArray = js("new Uint8Array(data.length)")
    for (i in data.indices) {
        jsArray[i] = data[i]
    }
    (writable.write(jsArray) as Promise<dynamic>).await()
}

private suspend fun closeWritable(writable: dynamic) {
    (writable.close() as Promise<dynamic>).await()
}

private suspend fun getFile(fileHandle: dynamic): dynamic = (fileHandle.getFile() as Promise<dynamic>).await()

private suspend fun fileToArrayBuffer(file: dynamic): dynamic = (file.arrayBuffer() as Promise<dynamic>).await()

@Suppress("UnusedParameter")
private fun int8ArrayToByteArray(buffer: dynamic): ByteArray {
    val int8Array = js("new Int8Array(buffer)")
    val length = int8Array.length as Int
    return ByteArray(length) { int8Array[it] as Byte }
}

private suspend fun removeEntry(
    dirHandle: dynamic,
    name: String,
) {
    try {
        (dirHandle.removeEntry(name) as Promise<dynamic>).await()
    } catch (_: dynamic) {
        // Ignore
    }
}
