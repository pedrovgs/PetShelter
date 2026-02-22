package com.petshelter.di

import com.petshelter.core.service.FeatureFlagProvider
import com.petshelter.core.service.InMemoryFeatureFlagProvider
import com.petshelter.core.service.IosLogger
import com.petshelter.core.service.IosMetricReporter
import com.petshelter.core.service.Logger
import com.petshelter.core.service.MetricReporter
import com.petshelter.core.storage.FileStorage
import com.petshelter.core.storage.IosFileStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module =
    module {
        single<FileStorage> { IosFileStorage() }
        single<Logger> { IosLogger() }
        single<MetricReporter> { IosMetricReporter() }
        single<FeatureFlagProvider> { InMemoryFeatureFlagProvider(get()) }
    }
