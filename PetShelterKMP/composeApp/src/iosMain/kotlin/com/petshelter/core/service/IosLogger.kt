package com.petshelter.core.service

import platform.Foundation.NSLog

class IosLogger : Logger {
    override fun debug(
        tag: String,
        message: String,
    ) {
        NSLog("D/$tag: $message")
    }

    override fun info(
        tag: String,
        message: String,
    ) {
        NSLog("I/$tag: $message")
    }

    override fun warn(
        tag: String,
        message: String,
    ) {
        NSLog("W/$tag: $message")
    }

    override fun error(
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        if (throwable != null) {
            NSLog("E/$tag: $message — ${throwable.stackTraceToString()}")
        } else {
            NSLog("E/$tag: $message")
        }
    }
}
