package com.petshelter.core.data

import com.petshelter.core.model.Animal
import com.petshelter.core.model.AnimalType
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.ExperimentalResourceApi
import petshelter.composeapp.generated.resources.Res

class AnimalRepository {
    private val json = Json { ignoreUnknownKeys = true }
    private var cachedAnimals: List<Animal>? = null

    suspend fun getAllAnimals(): List<Animal> = loadAnimals()

    suspend fun getAnimals(type: AnimalType): List<Animal> = loadAnimals().filter { it.animalType == type }

    suspend fun getAnimalById(id: String): Animal? = loadAnimals().find { it.id == id }

    @OptIn(ExperimentalResourceApi::class)
    private suspend fun loadAnimals(): List<Animal> {
        cachedAnimals?.let { return it }
        val bytes = Res.readBytes("files/studied_animals.json")
        val text = bytes.decodeToString()
        val animals = json.decodeFromString<List<Animal>>(text)
        cachedAnimals = animals
        return animals
    }
}
