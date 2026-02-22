package com.petshelter.core.model

import kotlin.js.Date

actual fun currentTimeMillis(): Long = Date.now().toLong()
