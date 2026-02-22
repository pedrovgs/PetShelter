package com.petshelter.core.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

@Composable
expect fun StatusBarEffect(color: Color)
