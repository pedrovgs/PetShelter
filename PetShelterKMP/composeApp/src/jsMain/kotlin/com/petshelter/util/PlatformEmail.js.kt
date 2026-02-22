package com.petshelter.util

import kotlinx.browser.window

actual fun openEmail(address: String) {
    window.open("mailto:$address")
}
