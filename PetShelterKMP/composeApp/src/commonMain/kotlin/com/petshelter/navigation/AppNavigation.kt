package com.petshelter.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.petshelter.components.BottomNavigationBar
import com.petshelter.components.NavigationSidebar
import com.petshelter.components.SidebarItem
import com.petshelter.designsystem.AnimationDuration
import com.petshelter.feature.adopt.AdoptScreen
import com.petshelter.feature.cats.CatsToAdoptScreen
import com.petshelter.feature.center.CenterInfoScreen
import com.petshelter.feature.contact.ContactScreen
import com.petshelter.feature.dogs.DogsToAdoptScreen

private val LARGE_SCREEN_BREAKPOINT = 600.dp

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    var selectedItem by rememberSaveable { mutableStateOf(SidebarItem.Adopt) }
    var sidebarCollapsed by rememberSaveable { mutableStateOf(false) }

    BoxWithConstraints(modifier = modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)) {
        val isLargeScreen = maxWidth >= LARGE_SCREEN_BREAKPOINT

        if (isLargeScreen) {
            LargeScreenLayout(
                navController = navController,
                selectedItem = selectedItem,
                sidebarCollapsed = sidebarCollapsed,
                onItemSelected = { item ->
                    selectedItem = item
                    navigateToItem(navController, item)
                },
                onToggleCollapsed = { sidebarCollapsed = !sidebarCollapsed },
            )
        } else {
            SmallScreenLayout(
                navController = navController,
                selectedItem = selectedItem,
                onItemSelected = { item ->
                    selectedItem = item
                    navigateToItem(navController, item)
                },
            )
        }
    }
}

@Composable
private fun LargeScreenLayout(
    navController: NavHostController,
    selectedItem: SidebarItem,
    sidebarCollapsed: Boolean,
    onItemSelected: (SidebarItem) -> Unit,
    onToggleCollapsed: () -> Unit,
) {
    Row(modifier = Modifier.fillMaxSize()) {
        NavigationSidebar(
            selectedItem = selectedItem,
            onItemSelected = onItemSelected,
            collapsed = sidebarCollapsed,
            onToggleCollapsed = onToggleCollapsed,
        )
        NavigationContent(
            navController = navController,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun SmallScreenLayout(
    navController: NavHostController,
    selectedItem: SidebarItem,
    onItemSelected: (SidebarItem) -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        NavigationContent(
            navController = navController,
            modifier = Modifier.weight(1f),
        )
        BottomNavigationBar(
            selectedItem = selectedItem,
            onItemSelected = onItemSelected,
        )
    }
}

@Composable
private fun NavigationContent(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = Route.Adopt,
            modifier = Modifier.fillMaxSize(),
            enterTransition = { fadeIn(tween(AnimationDuration.NORMAL)) },
            exitTransition = { fadeOut(tween(AnimationDuration.NORMAL)) },
            popEnterTransition = { fadeIn(tween(AnimationDuration.NORMAL)) },
            popExitTransition = { fadeOut(tween(AnimationDuration.NORMAL)) },
        ) {
            composable<Route.Adopt> {
                AdoptScreen(
                    onAnimalClick = { animalId ->
                        navController.navigate(Route.AnimalDetail(animalId))
                    },
                )
            }
            composable<Route.DogsToAdopt> {
                DogsToAdoptScreen(
                    onAnimalClick = { animalId ->
                        navController.navigate(Route.AnimalDetail(animalId))
                    },
                )
            }
            composable<Route.CatsToAdopt> {
                CatsToAdoptScreen(
                    onAnimalClick = { animalId ->
                        navController.navigate(Route.AnimalDetail(animalId))
                    },
                )
            }
            composable<Route.CenterInfo> {
                CenterInfoScreen()
            }
            composable<Route.Contact> {
                ContactScreen()
            }
            composable<Route.AnimalDetail> { backStackEntry ->
                val route = backStackEntry.toRoute<Route.AnimalDetail>()
                com.petshelter.feature.animals.AnimalDetailRoute(
                    animalId = route.animalId,
                    onBack = { navController.popBackStack() },
                )
            }
        }
    }
}

private fun navigateToItem(
    navController: NavHostController,
    item: SidebarItem,
) {
    val route: Route =
        when (item) {
            SidebarItem.Adopt -> Route.Adopt
            SidebarItem.DogsToAdopt -> Route.DogsToAdopt
            SidebarItem.CatsToAdopt -> Route.CatsToAdopt
            SidebarItem.CenterInfo -> Route.CenterInfo
            SidebarItem.Contact -> Route.Contact
        }
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
