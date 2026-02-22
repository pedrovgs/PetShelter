package com.petshelter.core.repository

import com.petshelter.core.model.Animal
import com.petshelter.core.model.AnimalType

interface AnimalRepository {
    suspend fun getAllAnimals(): List<Animal>

    suspend fun getAnimals(type: AnimalType): List<Animal>

    suspend fun getAnimalById(id: String): Animal?
}
