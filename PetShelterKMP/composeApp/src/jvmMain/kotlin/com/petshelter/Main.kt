package com.petshelter

import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.awt.Color
import java.awt.Taskbar
import javax.imageio.ImageIO

fun main() {
    System.setProperty("apple.awt.application.name", "PetShelter")

    val iconImage =
        Thread
            .currentThread()
            .contextClassLoader
            .getResourceAsStream("app_icon.png")
            ?.let { ImageIO.read(it) }

    if (iconImage != null && Taskbar.isTaskbarSupported()) {
        val taskbar = Taskbar.getTaskbar()
        if (taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
            taskbar.iconImage = iconImage
        }
    }

    application {
        val windowIcon = iconImage?.toComposeImageBitmap()?.let { BitmapPainter(it) }

        Window(
            onCloseRequest = ::exitApplication,
            title = "PetShelter",
            icon = windowIcon,
            state = rememberWindowState(width = 1440.dp, height = 900.dp),
        ) {
            window.background = Color.WHITE
            App()
        }
    }
}
