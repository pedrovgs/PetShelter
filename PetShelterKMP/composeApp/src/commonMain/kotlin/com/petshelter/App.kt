package com.petshelter

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.di.appModule
import com.petshelter.di.platformModule
import com.petshelter.navigation.AppNavigation
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration

@Composable
fun App(platformKoinConfig: KoinAppDeclaration = {}) {
    KoinApplication(application = {
        platformKoinConfig()
        modules(appModule, platformModule)
    }) {
        setSingletonImageLoaderFactory { context ->
            ImageLoader
                .Builder(context)
                .components { add(KtorNetworkFetcherFactory()) }
                .crossfade(true)
                .build()
        }
        PetShelterTheme {
            val navController = rememberNavController()
            AppNavigation(
                navController = navController,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
