package com.petshelter

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport

@OptIn(ExperimentalComposeUiApi::class)
fun startApp() {
    ComposeViewport {
        App()
    }
}
