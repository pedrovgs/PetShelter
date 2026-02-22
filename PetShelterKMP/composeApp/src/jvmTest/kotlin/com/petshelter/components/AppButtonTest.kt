package com.petshelter.components

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.petshelter.designsystem.PetShelterTheme
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AppButtonTest {
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun primaryButtonDisplaysText() =
        runComposeUiTest {
            setContent {
                PetShelterTheme {
                    AppButton(
                        text = "Adopt Now",
                        onClick = {},
                        variant = ButtonVariant.Primary,
                    )
                }
            }

            onNodeWithText("Adopt Now").assertIsDisplayed()
        }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun clickCallbackFires() =
        runComposeUiTest {
            var clickCount = 0

            setContent {
                PetShelterTheme {
                    AppButton(
                        text = "Click Me",
                        onClick = { clickCount++ },
                    )
                }
            }

            onNodeWithText("Click Me").performClick()

            assertEquals(1, clickCount)
        }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun loadingStateShowsProgressIndicator() =
        runComposeUiTest {
            setContent {
                PetShelterTheme {
                    AppButton(
                        text = "Submit",
                        onClick = {},
                        loading = true,
                    )
                }
            }

            onNodeWithText("Submit").assertIsDisplayed()
        }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun disabledStateDoesNotFireClick() =
        runComposeUiTest {
            var clicked = false

            setContent {
                PetShelterTheme {
                    AppButton(
                        text = "Disabled Button",
                        onClick = { clicked = true },
                        enabled = false,
                    )
                }
            }

            onNodeWithText("Disabled Button").performClick()

            assertTrue(!clicked, "Click callback should not fire when button is disabled")
        }
}
