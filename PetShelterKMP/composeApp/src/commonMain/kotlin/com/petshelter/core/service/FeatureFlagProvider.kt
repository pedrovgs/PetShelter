package com.petshelter.core.service

import kotlinx.coroutines.flow.Flow

interface FeatureFlagProvider {
    fun isEnabled(flag: FeatureFlag): Boolean

    fun observeFlag(flag: FeatureFlag): Flow<Boolean>
}
