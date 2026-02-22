package com.petshelter.util

interface ExternalNavigator {
    fun openUrl(url: String)

    fun openEmail(address: String)
}

class DefaultExternalNavigator : ExternalNavigator {
    override fun openUrl(url: String) {
        com.petshelter.util.openUrl(url)
    }

    override fun openEmail(address: String) {
        com.petshelter.util.openEmail(address)
    }
}
