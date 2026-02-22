package com.petshelter.designsystem

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

@Composable
actual fun observeSystemDarkTheme(): Boolean = isSystemInDarkTheme()
