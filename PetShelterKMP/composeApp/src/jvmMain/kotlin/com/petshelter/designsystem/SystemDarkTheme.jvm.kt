package com.petshelter.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
actual fun observeSystemDarkTheme(): Boolean {
    var isDark by remember { mutableStateOf(isDesktopDarkTheme()) }
    LaunchedEffect(Unit) {
        while (isActive) {
            delay(1000)
            isDark = isDesktopDarkTheme()
        }
    }
    return isDark
}

private fun isDesktopDarkTheme(): Boolean {
    val os = System.getProperty("os.name", "").lowercase()
    return when {
        os.contains("mac") -> isMacOsDarkTheme()
        os.contains("win") -> isWindowsDarkTheme()
        else -> isLinuxDarkTheme()
    }
}

private fun isMacOsDarkTheme(): Boolean =
    try {
        val process =
            ProcessBuilder("defaults", "read", "-g", "AppleInterfaceStyle")
                .redirectErrorStream(true)
                .start()
        val result =
            process.inputStream
                .bufferedReader()
                .readText()
                .trim()
        process.waitFor()
        result.equals("Dark", ignoreCase = true)
    } catch (_: Exception) {
        false
    }

private fun isWindowsDarkTheme(): Boolean =
    try {
        val process =
            ProcessBuilder(
                "reg",
                "query",
                "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize",
                "/v",
                "AppsUseLightTheme",
            ).redirectErrorStream(true).start()
        val result = process.inputStream.bufferedReader().readText()
        process.waitFor()
        result.contains("0x0")
    } catch (_: Exception) {
        false
    }

private fun isLinuxDarkTheme(): Boolean =
    try {
        val process =
            ProcessBuilder("gsettings", "get", "org.gnome.desktop.interface", "color-scheme")
                .redirectErrorStream(true)
                .start()
        val result =
            process.inputStream
                .bufferedReader()
                .readText()
                .trim()
        process.waitFor()
        result.contains("dark", ignoreCase = true)
    } catch (_: Exception) {
        false
    }
