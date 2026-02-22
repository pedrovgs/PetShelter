package com.petshelter

import kotlin.test.Test
import kotlin.test.assertNotNull

class IosAppStartSmokeTest {
    @Test
    fun mainViewControllerCreatesSuccessfully() {
        val viewController = MainViewController()
        assertNotNull(viewController)
    }
}
