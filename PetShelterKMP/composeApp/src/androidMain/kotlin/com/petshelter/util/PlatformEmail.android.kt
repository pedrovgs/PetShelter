package com.petshelter.util

actual fun openEmail(address: String) {
    // No-op: Android email opening requires Context which is not available in shared code
}
