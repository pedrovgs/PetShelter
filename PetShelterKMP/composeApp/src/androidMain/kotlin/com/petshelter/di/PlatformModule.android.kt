package com.petshelter.di

import com.petshelter.core.service.AndroidLogger
import com.petshelter.core.service.AndroidMetricReporter
import com.petshelter.core.service.FeatureFlagProvider
import com.petshelter.core.service.InMemoryFeatureFlagProvider
import com.petshelter.core.service.Logger
import com.petshelter.core.service.MetricReporter
import com.petshelter.core.storage.AndroidFileStorage
import com.petshelter.core.storage.FileStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module =
    module {
        single<FileStorage> { AndroidFileStorage(get()) }
        single<Logger> { AndroidLogger() }
        single<MetricReporter> { AndroidMetricReporter() }
        single<FeatureFlagProvider> { InMemoryFeatureFlagProvider(get()) }
    }
