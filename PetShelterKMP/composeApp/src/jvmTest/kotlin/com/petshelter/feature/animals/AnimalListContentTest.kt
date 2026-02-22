package com.petshelter.feature.animals

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.runComposeUiTest
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.testAnimal
import kotlin.test.Test

class AnimalListContentTest {
    @OptIn(ExperimentalTestApi::class)
    @Test
    fun loadingStateShowsLoadingIndicator() =
        runComposeUiTest {
            setContent {
                PetShelterTheme {
                    AnimalListContent(
                        state = AnimalListUiState(isLoading = true),
                        onAnimalClick = {},
                        onSexChanged = {},
                        onSizeChanged = {},
                        onBreedChanged = {},
                        onAgeChanged = {},
                    )
                }
            }

            onNodeWithText("0 animals found").assertIsDisplayed()
        }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun emptyStateShowsEmptyMessage() =
        runComposeUiTest {
            setContent {
                PetShelterTheme {
                    AnimalListContent(
                        state =
                            AnimalListUiState(
                                isLoading = false,
                                filteredAnimals = emptyList(),
                            ),
                        onAnimalClick = {},
                        onSexChanged = {},
                        onSizeChanged = {},
                        onBreedChanged = {},
                        onAgeChanged = {},
                    )
                }
            }

            onNodeWithText("No animals match your current filters. Try adjusting your search criteria.")
                .assertIsDisplayed()
        }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun populatedStateShowsAnimalNames() =
        runComposeUiTest {
            val animals =
                listOf(
                    testAnimal(id = "1", name = "Luna"),
                    testAnimal(id = "2", name = "Rocky"),
                    testAnimal(id = "3", name = "Bella"),
                )

            setContent {
                PetShelterTheme {
                    AnimalListContent(
                        state =
                            AnimalListUiState(
                                isLoading = false,
                                filteredAnimals = animals,
                            ),
                        onAnimalClick = {},
                        onSexChanged = {},
                        onSizeChanged = {},
                        onBreedChanged = {},
                        onAgeChanged = {},
                    )
                }
            }

            onNodeWithText("Luna").assertIsDisplayed()
            onNodeWithText("Rocky").assertIsDisplayed()
            onNodeWithText("Bella").assertIsDisplayed()
            onNodeWithText("3 animals found").assertIsDisplayed()
        }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun errorStateShowsErrorMessage() =
        runComposeUiTest {
            val errorText = "Failed to load animals from server"

            setContent {
                PetShelterTheme {
                    AnimalListContent(
                        state =
                            AnimalListUiState(
                                isLoading = false,
                                errorMessage = errorText,
                            ),
                        onAnimalClick = {},
                        onSexChanged = {},
                        onSizeChanged = {},
                        onBreedChanged = {},
                        onAgeChanged = {},
                    )
                }
            }

            onNodeWithText(errorText).assertIsDisplayed()
        }
}
