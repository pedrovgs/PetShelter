package com.petshelter.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Animal(
    val id: String,
    @SerialName("animal_type") val animalType: AnimalType,
    val name: String,
    val sex: String,
    val breed: String,
    val size: AnimalSize,
    @SerialName("age_months") val ageMonths: Int? = null,
    val description: String,
    val images: List<String>,
    val videos: List<String>,
    @SerialName("source_url") val sourceUrl: String,
    val scores: AnimalScores,
)

@Serializable
enum class AnimalType {
    @SerialName("dog")
    DOG,

    @SerialName("cat")
    CAT,
}

@Serializable
enum class AnimalSize {
    @SerialName("small")
    SMALL,

    @SerialName("medium")
    MEDIUM,

    @SerialName("large")
    LARGE,

    @SerialName("extra_large")
    EXTRA_LARGE,
}

@Serializable
data class AnimalScores(
    val friendly: Int,
    @SerialName("good_with_animals") val goodWithAnimals: Int,
    @SerialName("leash_trained") val leashTrained: Int,
    val reactive: Int,
    @SerialName("special_needs") val specialNeeds: Int,
    val energy: Int,
    @SerialName("good_with_humans") val goodWithHumans: Int,
    val shy: Int,
    val activity: Int,
    val trainability: Int,
    @SerialName("daily_activity_requirement") val dailyActivityRequirement: Int,
)
