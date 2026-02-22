package com.petshelter.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object DogsToAdopt : Route

    @Serializable
    data object CatsToAdopt : Route

    @Serializable
    data object CenterInfo : Route

    @Serializable
    data object Contact : Route

    @Serializable
    data class AnimalDetail(
        val animalId: String,
    ) : Route
}
