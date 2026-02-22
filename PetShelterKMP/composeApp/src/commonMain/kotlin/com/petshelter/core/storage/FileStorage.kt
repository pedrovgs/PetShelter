package com.petshelter.core.storage

interface FileStorage {
    suspend fun save(
        path: String,
        data: ByteArray,
    )

    suspend fun load(path: String): ByteArray?

    suspend fun delete(path: String)

    suspend fun exists(path: String): Boolean
}
