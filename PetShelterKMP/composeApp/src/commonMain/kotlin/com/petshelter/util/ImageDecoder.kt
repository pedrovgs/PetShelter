package com.petshelter.util

import androidx.compose.ui.graphics.ImageBitmap

expect fun ByteArray.decodeToImageBitmap(): ImageBitmap
