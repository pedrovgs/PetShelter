package com.petshelter.core.service

import android.util.Log

class AndroidLogger : Logger {
    override fun debug(
        tag: String,
        message: String,
    ) {
        Log.d(tag, message)
    }

    override fun info(
        tag: String,
        message: String,
    ) {
        Log.i(tag, message)
    }

    override fun warn(
        tag: String,
        message: String,
    ) {
        Log.w(tag, message)
    }

    override fun error(
        tag: String,
        message: String,
        throwable: Throwable?,
    ) {
        if (throwable != null) {
            Log.e(tag, message, throwable)
        } else {
            Log.e(tag, message)
        }
    }
}
