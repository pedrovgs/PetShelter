package com.petshelter.core.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

class JsPlatform : Platform {
    override val name: String = "Web with Kotlin/JS"
}

actual fun getPlatform(): Platform = JsPlatform()

@Composable
actual fun StatusBarEffect(color: Color) {
    // No-op: Web doesn't have a system status bar
}
