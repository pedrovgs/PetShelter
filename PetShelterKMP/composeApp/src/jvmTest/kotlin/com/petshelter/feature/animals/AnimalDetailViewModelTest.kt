package com.petshelter.feature.animals

import com.petshelter.core.repository.AnimalRepository
import com.petshelter.testAnimal
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AnimalDetailViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private val repository = mock<AnimalRepository>()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loads animal detail successfully`() {
        val animal = testAnimal(id = "dog-1", name = "Luna", description = "A gentle soul")
        everySuspend { repository.getAnimalById("dog-1") } returns animal

        val viewModel = AnimalDetailViewModel(animalId = "dog-1", repository = repository)

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNotNull(state.animal)
        assertEquals("Luna", state.animal?.name)
        assertNull(state.errorMessage)
    }

    @Test
    fun `sets error state when animal is not found`() {
        everySuspend { repository.getAnimalById("nonexistent") } returns null

        val viewModel = AnimalDetailViewModel(animalId = "nonexistent", repository = repository)

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNull(state.animal)
        assertEquals("Animal not found", state.errorMessage)
    }

    @Test
    fun `sets error state when repository throws`() {
        everySuspend { repository.getAnimalById("dog-1") } throws RuntimeException("Database error")

        val viewModel = AnimalDetailViewModel(animalId = "dog-1", repository = repository)

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNull(state.animal)
        assertEquals("Database error", state.errorMessage)
    }

    @Test
    fun `cleans youtube urls from description`() {
        val animal =
            testAnimal(
                id = "dog-1",
                description = "Meet Luna! https://www.youtube.com/watch?v=abc123 She is playful.",
            )
        everySuspend { repository.getAnimalById("dog-1") } returns animal

        val viewModel = AnimalDetailViewModel(animalId = "dog-1", repository = repository)

        val state = viewModel.uiState.value
        assertFalse(state.cleanDescription.contains("youtube"))
        assertTrue(state.cleanDescription.contains("Meet Luna!"))
        assertTrue(state.cleanDescription.contains("She is playful."))
    }

    @Test
    fun `extracts video links from videos field`() {
        val animal =
            testAnimal(
                id = "dog-1",
                videos = listOf("https://www.youtube.com/watch?v=abc", "https://youtu.be/def"),
                description = "A good dog.",
            )
        everySuspend { repository.getAnimalById("dog-1") } returns animal

        val viewModel = AnimalDetailViewModel(animalId = "dog-1", repository = repository)

        val videoLinks = viewModel.uiState.value.videoLinks
        assertEquals(2, videoLinks.size)
        assertTrue(videoLinks.contains("https://www.youtube.com/watch?v=abc"))
        assertTrue(videoLinks.contains("https://youtu.be/def"))
    }

    @Test
    fun `extracts video links from description`() {
        val animal =
            testAnimal(
                id = "dog-1",
                videos = emptyList(),
                description = "Check out https://www.youtube.com/watch?v=video1 for more info.",
            )
        everySuspend { repository.getAnimalById("dog-1") } returns animal

        val viewModel = AnimalDetailViewModel(animalId = "dog-1", repository = repository)

        val videoLinks = viewModel.uiState.value.videoLinks
        assertEquals(1, videoLinks.size)
        assertTrue(videoLinks.first().contains("youtube.com"))
    }

    @Test
    fun `deduplicates video links from videos field and description`() {
        val youtubeUrl = "https://www.youtube.com/watch?v=shared"
        val animal =
            testAnimal(
                id = "dog-1",
                videos = listOf(youtubeUrl),
                description = "Watch here: $youtubeUrl for details.",
            )
        everySuspend { repository.getAnimalById("dog-1") } returns animal

        val viewModel = AnimalDetailViewModel(animalId = "dog-1", repository = repository)

        val videoLinks = viewModel.uiState.value.videoLinks
        assertEquals(1, videoLinks.size)
    }
}
