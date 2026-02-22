package com.petshelter

import com.petshelter.core.model.Animal
import com.petshelter.core.model.AnimalScores
import com.petshelter.core.model.AnimalSex
import com.petshelter.core.model.AnimalSize
import com.petshelter.core.model.AnimalType

fun testAnimal(
    id: String = "test-1",
    animalType: AnimalType = AnimalType.DOG,
    name: String = "Luna",
    sex: AnimalSex = AnimalSex.FEMALE,
    breed: String = "Labrador",
    size: AnimalSize = AnimalSize.MEDIUM,
    ageMonths: Int? = 24,
    description: String = "A friendly dog looking for a home.",
    images: List<String> = emptyList(),
    videos: List<String> = emptyList(),
    sourceUrl: String = "https://example.com/luna",
    scores: AnimalScores = testAnimalScores(),
) = Animal(
    id = id,
    animalType = animalType,
    name = name,
    sex = sex,
    breed = breed,
    size = size,
    ageMonths = ageMonths,
    description = description,
    images = images,
    videos = videos,
    sourceUrl = sourceUrl,
    scores = scores,
)

fun testAnimalScores(
    friendly: Int = 7,
    goodWithAnimals: Int = 6,
    leashTrained: Int = 5,
    reactive: Int = 3,
    specialNeeds: Int = 1,
    energy: Int = 8,
    goodWithHumans: Int = 7,
    shy: Int = 2,
    activity: Int = 7,
    trainability: Int = 8,
    dailyActivityRequirement: Int = 7,
) = AnimalScores(
    friendly = friendly,
    goodWithAnimals = goodWithAnimals,
    leashTrained = leashTrained,
    reactive = reactive,
    specialNeeds = specialNeeds,
    energy = energy,
    goodWithHumans = goodWithHumans,
    shy = shy,
    activity = activity,
    trainability = trainability,
    dailyActivityRequirement = dailyActivityRequirement,
)
