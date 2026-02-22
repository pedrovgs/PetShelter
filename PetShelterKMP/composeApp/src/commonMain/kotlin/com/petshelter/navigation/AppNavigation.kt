package com.petshelter.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.petshelter.designsystem.AnimationDuration
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)) {
        NavHost(
            navController = navController,
            startDestination = Route.Home,
            modifier = Modifier.fillMaxSize(),
            enterTransition = { fadeIn(tween(AnimationDuration.NORMAL)) },
            exitTransition = { fadeOut(tween(AnimationDuration.NORMAL)) },
            popEnterTransition = { fadeIn(tween(AnimationDuration.NORMAL)) },
            popExitTransition = { fadeOut(tween(AnimationDuration.NORMAL)) },
        ) {
            composable<Route.Home> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "PetShelter",
                        style = PetShelterTypography.Heading1,
                        color = PetShelterTheme.colors.TextPrimary,
                    )
                }
            }
        }
    }
}
