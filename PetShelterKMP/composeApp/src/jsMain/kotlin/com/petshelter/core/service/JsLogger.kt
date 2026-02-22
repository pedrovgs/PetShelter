package com.petshelter.core.service

class JsLogger : Logger {
    override fun debug(
        tag: String,
        message: String,
    ) {
        console.log("D/$tag: $message")
    }

    override fun info(
        tag: String,
        message: String,
    ) {
        console.info("I/$tag: $message")
    }

    override fun warn(
        tag: String,
        message: String,
    ) {
        console.warn("W/$tag: $message")
    }

    override fun error(
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        if (throwable != null) {
            console.error("E/$tag: $message — ${throwable.stackTraceToString()}")
        } else {
            console.error("E/$tag: $message")
        }
    }
}
