package com.petshelter.util

import java.awt.Desktop
import java.net.URI

actual fun openEmail(address: String) {
    val uri = URI("mailto:$address")
    try {
        if (Desktop.isDesktopSupported()) {
            val desktop = Desktop.getDesktop()
            if (desktop.isSupported(Desktop.Action.MAIL)) {
                desktop.mail(uri)
            } else if (desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(uri)
            }
        }
    } catch (_: Exception) {
        // Fallback: try browse as some systems handle mailto: via the browser
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri)
            }
        } catch (_: Exception) {
            // No email client available
        }
    }
}
