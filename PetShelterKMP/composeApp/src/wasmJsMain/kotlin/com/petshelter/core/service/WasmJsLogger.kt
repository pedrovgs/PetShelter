package com.petshelter.core.service

class WasmJsLogger : Logger {
    override fun debug(
        tag: String,
        message: String,
    ) {
        println("D/$tag: $message")
    }

    override fun info(
        tag: String,
        message: String,
    ) {
        println("I/$tag: $message")
    }

    override fun warn(
        tag: String,
        message: String,
    ) {
        println("W/$tag: $message")
    }

    override fun error(
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        if (throwable != null) {
            println("E/$tag: $message — ${throwable.stackTraceToString()}")
        } else {
            println("E/$tag: $message")
        }
    }
}
