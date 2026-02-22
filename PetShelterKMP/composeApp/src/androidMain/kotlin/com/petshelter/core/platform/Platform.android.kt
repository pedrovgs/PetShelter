package com.petshelter.core.platform

import android.app.Activity
import android.os.Build
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

@Composable
actual fun StatusBarEffect(color: Color) {
    val view = LocalView.current
    DisposableEffect(color) {
        val activity = view.context as? Activity ?: return@DisposableEffect onDispose {}
        (activity as? androidx.activity.ComponentActivity)?.enableEdgeToEdge(
            statusBarStyle =
                SystemBarStyle.light(
                    color.toArgb(),
                    android.graphics.Color.BLACK,
                ),
        )
        onDispose {
            (activity as? androidx.activity.ComponentActivity)?.enableEdgeToEdge()
        }
    }
}
