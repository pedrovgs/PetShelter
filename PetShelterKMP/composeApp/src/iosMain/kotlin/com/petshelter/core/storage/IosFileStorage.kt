package com.petshelter.core.storage

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import platform.Foundation.NSData
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask
import platform.Foundation.create
import platform.Foundation.dataWithContentsOfFile
import platform.Foundation.writeToFile
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
class IosFileStorage : FileStorage {
    private val documentsDirectory: String by lazy {
        val paths =
            NSSearchPathForDirectoriesInDomains(
                NSDocumentDirectory,
                NSUserDomainMask,
                true,
            )
        paths.first() as String
    }

    override suspend fun save(
        path: String,
        data: ByteArray,
    ) = withContext(Dispatchers.Default) {
        val fullPath = resolvePath(path)
        ensureParentDirectoryExists(fullPath)
        val nsData = byteArrayToNSData(data)
        nsData.writeToFile(fullPath, atomically = true)
        Unit
    }

    override suspend fun load(path: String): ByteArray? =
        withContext(Dispatchers.Default) {
            val fullPath = resolvePath(path)
            val nsData = NSData.dataWithContentsOfFile(fullPath) ?: return@withContext null
            nsDataToByteArray(nsData)
        }

    override suspend fun delete(path: String) =
        withContext(Dispatchers.Default) {
            val fullPath = resolvePath(path)
            NSFileManager.defaultManager.removeItemAtPath(fullPath, null)
            Unit
        }

    override suspend fun exists(path: String): Boolean =
        withContext(Dispatchers.Default) {
            NSFileManager.defaultManager.fileExistsAtPath(resolvePath(path))
        }

    private fun resolvePath(path: String): String = "$documentsDirectory/$path"

    private fun ensureParentDirectoryExists(fullPath: String) {
        val parent = fullPath.substringBeforeLast('/')
        NSFileManager.defaultManager.createDirectoryAtPath(
            parent,
            withIntermediateDirectories = true,
            attributes = null,
            error = null,
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun byteArrayToNSData(bytes: ByteArray): NSData {
    if (bytes.isEmpty()) return NSData()
    return bytes.usePinned { pinned ->
        NSData.create(bytes = pinned.addressOf(0), length = bytes.size.toULong())
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun nsDataToByteArray(data: NSData): ByteArray {
    val length = data.length.toInt()
    if (length == 0) return ByteArray(0)
    val bytes = ByteArray(length)
    bytes.usePinned { pinned ->
        memcpy(pinned.addressOf(0), data.bytes, data.length)
    }
    return bytes
}
