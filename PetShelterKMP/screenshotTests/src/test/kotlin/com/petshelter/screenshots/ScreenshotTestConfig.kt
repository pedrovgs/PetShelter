package com.petshelter.screenshots

import androidx.compose.runtime.Composable
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.resources.Density
import com.android.resources.ScreenOrientation
import com.petshelter.designsystem.PetShelterTheme

enum class ThemeVariant {
    Light,
    Dark,
}

enum class DevicePreset(
    val screenWidth: Int,
    val screenHeight: Int,
    val density: Density,
) {
    PHONE(screenWidth = 390, screenHeight = 844, density = Density.XXHIGH),
    TABLET(screenWidth = 820, screenHeight = 1180, density = Density.XHIGH),
    DESKTOP(screenWidth = 1440, screenHeight = 900, density = Density.XHIGH),
}

fun paparazziRule(device: DevicePreset = DevicePreset.PHONE): Paparazzi =
    Paparazzi(
        deviceConfig =
            DeviceConfig(
                screenWidth = device.screenWidth,
                screenHeight = device.screenHeight,
                density = device.density,
                orientation = ScreenOrientation.PORTRAIT,
            ),
        theme = "android:Theme.Material.Light.NoActionBar",
    )

@Composable
fun ThemedContent(
    theme: ThemeVariant,
    content: @Composable () -> Unit,
) {
    PetShelterTheme(darkTheme = theme == ThemeVariant.Dark) {
        content()
    }
}
