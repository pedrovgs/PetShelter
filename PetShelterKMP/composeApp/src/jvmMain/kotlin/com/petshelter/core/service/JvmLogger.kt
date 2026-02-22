package com.petshelter.core.service

class JvmLogger : Logger {
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
        System.err.println("E/$tag: $message")
        throwable?.printStackTrace(System.err)
    }
}
