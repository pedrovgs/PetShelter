package com.petshelter.core.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class InMemoryFeatureFlagProvider(
    private val logger: Logger,
) : FeatureFlagProvider {
    private val overrides = MutableStateFlow<Map<String, Boolean>>(emptyMap())

    override fun isEnabled(flag: FeatureFlag): Boolean {
        val value = overrides.value[flag.key] ?: flag.defaultValue
        logger.debug("FeatureFlag", "isEnabled(${flag.key}) = $value")
        return value
    }

    override fun observeFlag(flag: FeatureFlag): Flow<Boolean> = overrides.map { it[flag.key] ?: flag.defaultValue }

    fun setOverride(
        flag: FeatureFlag,
        enabled: Boolean,
    ) {
        overrides.value = overrides.value + (flag.key to enabled)
    }
}
