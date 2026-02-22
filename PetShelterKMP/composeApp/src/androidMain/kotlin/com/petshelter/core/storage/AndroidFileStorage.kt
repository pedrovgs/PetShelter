package com.petshelter.core.storage

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class AndroidFileStorage(
    private val context: Context,
) : FileStorage {
    override suspend fun save(
        path: String,
        data: ByteArray,
    ) = withContext(Dispatchers.IO) {
        val file = resolveFile(path)
        file.parentFile?.mkdirs()
        file.writeBytes(data)
    }

    override suspend fun load(path: String): ByteArray? =
        withContext(Dispatchers.IO) {
            val file = resolveFile(path)
            if (file.exists()) file.readBytes() else null
        }

    override suspend fun delete(path: String) =
        withContext(Dispatchers.IO) {
            resolveFile(path).delete()
            Unit
        }

    override suspend fun exists(path: String): Boolean =
        withContext(Dispatchers.IO) {
            resolveFile(path).exists()
        }

    private fun resolveFile(path: String): File = File(context.filesDir, path)
}
