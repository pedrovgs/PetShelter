package com.petshelter.screenshots.components

import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import com.petshelter.components.AppButton
import com.petshelter.components.ButtonVariant
import com.petshelter.screenshots.DevicePreset
import com.petshelter.screenshots.ThemeVariant
import com.petshelter.screenshots.ThemedContent
import com.petshelter.screenshots.paparazziRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class AppButtonScreenshotTest(
    @TestParameter val theme: ThemeVariant,
) {
    @get:Rule
    val paparazzi = paparazziRule(DevicePreset.PHONE)

    @Test
    fun primaryButton() {
        paparazzi.snapshot {
            ThemedContent(theme) {
                AppButton(text = "Primary", onClick = {})
            }
        }
    }

    @Test
    fun secondaryButton() {
        paparazzi.snapshot {
            ThemedContent(theme) {
                AppButton(
                    text = "Secondary",
                    onClick = {},
                    variant = ButtonVariant.Secondary,
                )
            }
        }
    }

    @Test
    fun ghostButton() {
        paparazzi.snapshot {
            ThemedContent(theme) {
                AppButton(
                    text = "Ghost",
                    onClick = {},
                    variant = ButtonVariant.Ghost,
                )
            }
        }
    }

    @Test
    fun dangerButton() {
        paparazzi.snapshot {
            ThemedContent(theme) {
                AppButton(
                    text = "Delete",
                    onClick = {},
                    variant = ButtonVariant.Danger,
                )
            }
        }
    }
}
