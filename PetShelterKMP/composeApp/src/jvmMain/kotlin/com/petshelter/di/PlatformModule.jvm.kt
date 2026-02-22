package com.petshelter.di

import com.petshelter.core.service.FeatureFlagProvider
import com.petshelter.core.service.InMemoryFeatureFlagProvider
import com.petshelter.core.service.JvmLogger
import com.petshelter.core.service.JvmMetricReporter
import com.petshelter.core.service.Logger
import com.petshelter.core.service.MetricReporter
import com.petshelter.core.storage.FileStorage
import com.petshelter.core.storage.JvmFileStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module =
    module {
        single<FileStorage> { JvmFileStorage() }
        single<Logger> { JvmLogger() }
        single<MetricReporter> { JvmMetricReporter() }
        single<FeatureFlagProvider> { InMemoryFeatureFlagProvider(get()) }
    }
