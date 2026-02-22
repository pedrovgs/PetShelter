package com.petshelter.core.data

import com.petshelter.core.model.Animal
import com.petshelter.core.model.AnimalType
import com.petshelter.core.repository.AnimalRepository
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import petshelter.composeapp.generated.resources.Res

class AnimalRepositoryImpl : AnimalRepository {
    private val json = Json { ignoreUnknownKeys = true }
    private val mutex = Mutex()
    private var cachedAnimals: List<Animal>? = null

    override suspend fun getAllAnimals(): List<Animal> = loadAnimals()

    override suspend fun getAnimals(type: AnimalType): List<Animal> = loadAnimals().filter { it.animalType == type }

    override suspend fun getAnimalById(id: String): Animal? = loadAnimals().find { it.id == id }

    @OptIn(ExperimentalResourceApi::class)
    private suspend fun loadAnimals(): List<Animal> =
        mutex.withLock {
            cachedAnimals?.let { return it }
            val bytes = Res.readBytes("files/studied_animals.json")
            val text = bytes.decodeToString()
            val animals = json.decodeFromString<List<Animal>>(text)
            cachedAnimals = animals
            animals
        }
}
