package com.petshelter.di

import com.petshelter.core.service.FeatureFlagProvider
import com.petshelter.core.service.InMemoryFeatureFlagProvider
import com.petshelter.core.service.Logger
import com.petshelter.core.service.MetricReporter
import com.petshelter.core.service.WasmJsLogger
import com.petshelter.core.service.WasmJsMetricReporter
import com.petshelter.core.storage.FileStorage
import com.petshelter.core.storage.OpfsFileStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module =
    module {
        single<FileStorage> { OpfsFileStorage() }
        single<Logger> { WasmJsLogger() }
        single<MetricReporter> { WasmJsMetricReporter() }
        single<FeatureFlagProvider> { InMemoryFeatureFlagProvider(get()) }
    }
