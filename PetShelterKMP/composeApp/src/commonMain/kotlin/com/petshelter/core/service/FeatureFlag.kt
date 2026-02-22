package com.petshelter.core.service

sealed interface FeatureFlag {
    val key: String
    val defaultValue: Boolean
}
