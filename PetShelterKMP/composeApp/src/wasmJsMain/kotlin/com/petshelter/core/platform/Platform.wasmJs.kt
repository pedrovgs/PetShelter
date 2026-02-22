package com.petshelter.core.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

class WasmPlatform : Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

@Composable
actual fun StatusBarEffect(color: Color) {
    // No-op: Web doesn't have a system status bar
}
