package com.petshelter.screenshots.components

import androidx.compose.foundation.layout.width
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import com.petshelter.core.model.Animal
import com.petshelter.core.model.AnimalScores
import com.petshelter.core.model.AnimalSex
import com.petshelter.core.model.AnimalSize
import com.petshelter.core.model.AnimalType
import com.petshelter.feature.animals.AnimalCard
import com.petshelter.screenshots.DevicePreset
import com.petshelter.screenshots.ThemeVariant
import com.petshelter.screenshots.ThemedContent
import com.petshelter.screenshots.paparazziRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
class AnimalCardScreenshotTest(
    @TestParameter val theme: ThemeVariant,
) {
    @get:Rule
    val paparazzi = paparazziRule(DevicePreset.PHONE)

    @Test
    fun animalCard() {
        paparazzi.snapshot {
            ThemedContent(theme) {
                AnimalCard(
                    animal = testAnimal(),
                    onClick = {},
                    modifier = Modifier.width(220.dp),
                )
            }
        }
    }
}

private fun testAnimal(): Animal =
    Animal(
        id = "test-1",
        animalType = AnimalType.DOG,
        name = "Luna",
        sex = AnimalSex.FEMALE,
        breed = "Golden Retriever",
        size = AnimalSize.LARGE,
        ageMonths = 36,
        description = "Luna is a friendly Golden Retriever who loves playing fetch.",
        images = emptyList(),
        videos = emptyList(),
        sourceUrl = "https://example.com",
        scores =
            AnimalScores(
                friendly = 9,
                goodWithAnimals = 8,
                leashTrained = 7,
                reactive = 3,
                specialNeeds = 1,
                energy = 8,
                goodWithHumans = 9,
                shy = 2,
                activity = 8,
                trainability = 9,
                dailyActivityRequirement = 7,
            ),
    )
