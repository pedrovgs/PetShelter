package com.petshelter.designsystem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

val LocalPetShelterColors = staticCompositionLocalOf { LightPetShelterColors }

private val LightColorScheme =
    lightColorScheme(
        primary = LightPetShelterColors.Primary,
        onPrimary = LightPetShelterColors.TextInverse,
        primaryContainer = LightPetShelterColors.PrimaryLight,
        onPrimaryContainer = LightPetShelterColors.Theme,
        secondary = LightPetShelterColors.Theme,
        onSecondary = LightPetShelterColors.TextInverse,
        secondaryContainer = LightPetShelterColors.BackgroundSecondary,
        onSecondaryContainer = LightPetShelterColors.TextPrimary,
        tertiary = LightPetShelterColors.Info,
        onTertiary = LightPetShelterColors.TextInverse,
        background = LightPetShelterColors.BackgroundPrimary,
        onBackground = LightPetShelterColors.TextPrimary,
        surface = LightPetShelterColors.BackgroundPrimary,
        onSurface = LightPetShelterColors.TextPrimary,
        surfaceVariant = LightPetShelterColors.BackgroundSecondary,
        onSurfaceVariant = LightPetShelterColors.TextSecondary,
        outline = LightPetShelterColors.Border,
        outlineVariant = LightPetShelterColors.BorderLight,
        error = LightPetShelterColors.Error,
        onError = LightPetShelterColors.TextInverse,
        errorContainer = LightPetShelterColors.ErrorLight,
        onErrorContainer = LightPetShelterColors.Error,
    )

private val DarkColorScheme =
    darkColorScheme(
        primary = DarkPetShelterColors.Primary,
        onPrimary = DarkPetShelterColors.TextInverse,
        primaryContainer = DarkPetShelterColors.PrimaryLight,
        onPrimaryContainer = DarkPetShelterColors.Theme,
        secondary = DarkPetShelterColors.Theme,
        onSecondary = DarkPetShelterColors.TextInverse,
        secondaryContainer = DarkPetShelterColors.BackgroundSecondary,
        onSecondaryContainer = DarkPetShelterColors.TextPrimary,
        tertiary = DarkPetShelterColors.Info,
        onTertiary = DarkPetShelterColors.TextInverse,
        background = DarkPetShelterColors.BackgroundPrimary,
        onBackground = DarkPetShelterColors.TextPrimary,
        surface = DarkPetShelterColors.BackgroundPrimary,
        onSurface = DarkPetShelterColors.TextPrimary,
        surfaceVariant = DarkPetShelterColors.BackgroundSecondary,
        onSurfaceVariant = DarkPetShelterColors.TextSecondary,
        outline = DarkPetShelterColors.Border,
        outlineVariant = DarkPetShelterColors.BorderLight,
        error = DarkPetShelterColors.Error,
        onError = DarkPetShelterColors.TextInverse,
        errorContainer = DarkPetShelterColors.ErrorLight,
        onErrorContainer = DarkPetShelterColors.Error,
    )

object PetShelterTheme {
    val colors: PetShelterColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalPetShelterColors.current
}

@Composable
fun PetShelterTheme(
    darkTheme: Boolean = observeSystemDarkTheme(),
    content: @Composable () -> Unit,
) {
    val petshelterColors = if (darkTheme) DarkPetShelterColors else LightPetShelterColors
    val materialColorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    CompositionLocalProvider(LocalPetShelterColors provides petshelterColors) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            typography = AppTypography,
            content = content,
        )
    }
}
