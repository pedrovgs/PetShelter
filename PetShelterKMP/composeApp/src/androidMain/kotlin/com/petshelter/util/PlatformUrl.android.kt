package com.petshelter.util

actual fun openUrl(url: String) {
    // No-op: Android URL opening requires Context which is not available in shared code
}
