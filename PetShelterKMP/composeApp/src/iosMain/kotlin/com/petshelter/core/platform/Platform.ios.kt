package com.petshelter.core.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@Composable
actual fun StatusBarEffect(color: Color) {
    // No-op: iOS status bar is managed by the system
}
