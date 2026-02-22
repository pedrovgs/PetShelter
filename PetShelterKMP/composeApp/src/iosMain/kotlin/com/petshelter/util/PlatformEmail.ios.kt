package com.petshelter.util

actual fun openEmail(address: String) {
    // No-op: iOS email opening requires UIApplication which is not available in shared code
}
