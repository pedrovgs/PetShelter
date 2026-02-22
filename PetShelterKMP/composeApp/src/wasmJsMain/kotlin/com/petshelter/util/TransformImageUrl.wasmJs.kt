package com.petshelter.util

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
private fun encodeURIComponent(value: JsString): JsString = js("encodeURIComponent(value)")

@OptIn(kotlin.js.ExperimentalWasmJsInterop::class)
actual fun transformImageUrl(url: String): String {
    val encoded = encodeURIComponent(url.toJsString()).toString()
    return "https://corsproxy.io/?$encoded"
}
