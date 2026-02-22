package com.petshelter.util

actual fun transformImageUrl(url: String): String {
    val encoded = js("encodeURIComponent(url)") as String
    return "https://corsproxy.io/?$encoded"
}
