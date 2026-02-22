package com.petshelter.core.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

class JVMPlatform : Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

@Composable
actual fun StatusBarEffect(color: Color) {
    // No-op: Desktop doesn't have a system status bar
}
