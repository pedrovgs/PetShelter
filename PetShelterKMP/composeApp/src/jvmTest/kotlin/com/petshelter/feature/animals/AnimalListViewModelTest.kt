package com.petshelter.feature.animals

import com.petshelter.core.model.AnimalSex
import com.petshelter.core.model.AnimalSize
import com.petshelter.core.model.AnimalType
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
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class AnimalListViewModelTest {
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
    fun `loads all animals when no animal type is specified`() {
        val animals =
            listOf(
                testAnimal(id = "1", name = "Luna"),
                testAnimal(id = "2", name = "Rocky"),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(2, state.animals.size)
        assertEquals(2, state.filteredAnimals.size)
        assertNull(state.errorMessage)
    }

    @Test
    fun `loads animals filtered by type when animal type is specified`() {
        val dogs = listOf(testAnimal(id = "1", name = "Rex", animalType = AnimalType.DOG))
        everySuspend { repository.getAnimals(AnimalType.DOG) } returns dogs

        val viewModel = AnimalListViewModel(animalType = AnimalType.DOG, repository = repository)

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(1, state.animals.size)
        assertEquals("Rex", state.animals.first().name)
    }

    @Test
    fun `computes available breeds from loaded animals`() {
        val animals =
            listOf(
                testAnimal(id = "1", breed = "Labrador"),
                testAnimal(id = "2", breed = "Beagle"),
                testAnimal(id = "3", breed = "Labrador"),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)

        val breeds = viewModel.uiState.value.availableBreeds
        assertEquals(listOf("Beagle", "Labrador"), breeds)
    }

    @Test
    fun `filters animals by sex`() {
        val animals =
            listOf(
                testAnimal(id = "1", name = "Luna", sex = AnimalSex.FEMALE),
                testAnimal(id = "2", name = "Rocky", sex = AnimalSex.MALE),
                testAnimal(id = "3", name = "Bella", sex = AnimalSex.FEMALE),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)
        viewModel.onSexFilterChanged(AnimalSex.FEMALE)

        val state = viewModel.uiState.value
        assertEquals(2, state.filteredAnimals.size)
        assertTrue(state.filteredAnimals.all { it.sex == AnimalSex.FEMALE })
    }

    @Test
    fun `clearing sex filter shows all animals`() {
        val animals =
            listOf(
                testAnimal(id = "1", sex = AnimalSex.FEMALE),
                testAnimal(id = "2", sex = AnimalSex.MALE),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)
        viewModel.onSexFilterChanged(AnimalSex.FEMALE)
        viewModel.onSexFilterChanged(null)

        assertEquals(2, viewModel.uiState.value.filteredAnimals.size)
    }

    @Test
    fun `filters animals by size`() {
        val animals =
            listOf(
                testAnimal(id = "1", name = "Tiny", size = AnimalSize.SMALL),
                testAnimal(id = "2", name = "Big", size = AnimalSize.LARGE),
                testAnimal(id = "3", name = "Medium", size = AnimalSize.MEDIUM),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)
        viewModel.onSizeFilterChanged(AnimalSize.SMALL)

        val filtered = viewModel.uiState.value.filteredAnimals
        assertEquals(1, filtered.size)
        assertEquals("Tiny", filtered.first().name)
    }

    @Test
    fun `filters animals by breed`() {
        val animals =
            listOf(
                testAnimal(id = "1", name = "Luna", breed = "Labrador"),
                testAnimal(id = "2", name = "Rex", breed = "German Shepherd"),
                testAnimal(id = "3", name = "Buddy", breed = "Labrador"),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)
        viewModel.onBreedFilterChanged("German Shepherd")

        val filtered = viewModel.uiState.value.filteredAnimals
        assertEquals(1, filtered.size)
        assertEquals("Rex", filtered.first().name)
    }

    @Test
    fun `filters animals by age`() {
        val animals =
            listOf(
                testAnimal(id = "1", name = "Puppy", ageMonths = 4),
                testAnimal(id = "2", name = "Young", ageMonths = 18),
                testAnimal(id = "3", name = "Adult", ageMonths = 60),
                testAnimal(id = "4", name = "Unknown Age", ageMonths = null),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)
        viewModel.onAgeFilterChanged(AgeFilter.UP_TO_1_YEAR)

        val filtered = viewModel.uiState.value.filteredAnimals
        assertEquals(1, filtered.size)
        assertEquals("Puppy", filtered.first().name)
    }

    @Test
    fun `age filter excludes animals with null age`() {
        val animals =
            listOf(
                testAnimal(id = "1", name = "NoAge", ageMonths = null),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)
        viewModel.onAgeFilterChanged(AgeFilter.UP_TO_2_YEARS)

        assertTrue(
            viewModel.uiState.value.filteredAnimals
                .isEmpty(),
        )
    }

    @Test
    fun `search query matches animal name case-insensitively`() {
        val animals =
            listOf(
                testAnimal(id = "1", name = "Luna", breed = "Labrador"),
                testAnimal(id = "2", name = "Rocky", breed = "Beagle"),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)
        viewModel.onSearchQueryChanged("luna")

        val filtered = viewModel.uiState.value.filteredAnimals
        assertEquals(1, filtered.size)
        assertEquals("Luna", filtered.first().name)
    }

    @Test
    fun `search query matches animal breed`() {
        val animals =
            listOf(
                testAnimal(id = "1", name = "Luna", breed = "Labrador"),
                testAnimal(id = "2", name = "Rocky", breed = "Beagle"),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)
        viewModel.onSearchQueryChanged("beagle")

        val filtered = viewModel.uiState.value.filteredAnimals
        assertEquals(1, filtered.size)
        assertEquals("Rocky", filtered.first().name)
    }

    @Test
    fun `blank search query shows all animals`() {
        val animals =
            listOf(
                testAnimal(id = "1", name = "Luna"),
                testAnimal(id = "2", name = "Rocky"),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)
        viewModel.onSearchQueryChanged("Luna")
        viewModel.onSearchQueryChanged("  ")

        assertEquals(2, viewModel.uiState.value.filteredAnimals.size)
    }

    @Test
    fun `filters animals by animal type`() {
        val animals =
            listOf(
                testAnimal(id = "1", name = "Luna", animalType = AnimalType.DOG),
                testAnimal(id = "2", name = "Whiskers", animalType = AnimalType.CAT),
                testAnimal(id = "3", name = "Rex", animalType = AnimalType.DOG),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)
        viewModel.onAnimalTypeFilterChanged(AnimalType.CAT)

        val filtered = viewModel.uiState.value.filteredAnimals
        assertEquals(1, filtered.size)
        assertEquals("Whiskers", filtered.first().name)
    }

    @Test
    fun `breed list recalculates when animal type filter changes`() {
        val animals =
            listOf(
                testAnimal(id = "1", animalType = AnimalType.DOG, breed = "Labrador"),
                testAnimal(id = "2", animalType = AnimalType.CAT, breed = "Siamese"),
                testAnimal(id = "3", animalType = AnimalType.DOG, breed = "Beagle"),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)

        assertEquals(listOf("Beagle", "Labrador", "Siamese"), viewModel.uiState.value.availableBreeds)

        viewModel.onAnimalTypeFilterChanged(AnimalType.DOG)
        assertEquals(listOf("Beagle", "Labrador"), viewModel.uiState.value.availableBreeds)

        viewModel.onAnimalTypeFilterChanged(AnimalType.CAT)
        assertEquals(listOf("Siamese"), viewModel.uiState.value.availableBreeds)
    }

    @Test
    fun `changing animal type filter clears selected breed`() {
        val animals =
            listOf(
                testAnimal(id = "1", animalType = AnimalType.DOG, breed = "Labrador"),
                testAnimal(id = "2", animalType = AnimalType.CAT, breed = "Siamese"),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)
        viewModel.onBreedFilterChanged("Labrador")
        assertEquals("Labrador", viewModel.uiState.value.selectedBreed)

        viewModel.onAnimalTypeFilterChanged(AnimalType.CAT)
        assertNull(viewModel.uiState.value.selectedBreed)
    }

    @Test
    fun `multiple filters combine with AND logic`() {
        val animals =
            listOf(
                testAnimal(id = "1", name = "Luna", sex = AnimalSex.FEMALE, size = AnimalSize.SMALL),
                testAnimal(id = "2", name = "Bella", sex = AnimalSex.FEMALE, size = AnimalSize.LARGE),
                testAnimal(id = "3", name = "Rocky", sex = AnimalSex.MALE, size = AnimalSize.SMALL),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)
        viewModel.onSexFilterChanged(AnimalSex.FEMALE)
        viewModel.onSizeFilterChanged(AnimalSize.SMALL)

        val filtered = viewModel.uiState.value.filteredAnimals
        assertEquals(1, filtered.size)
        assertEquals("Luna", filtered.first().name)
    }

    @Test
    fun `handles repository error`() {
        everySuspend { repository.getAllAnimals() } throws RuntimeException("Network failure")

        val viewModel = AnimalListViewModel(animalType = null, repository = repository)

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals("Network failure", state.errorMessage)
        assertTrue(state.animals.isEmpty())
    }

    @Test
    fun `handles repository error for typed animals`() {
        everySuspend { repository.getAnimals(AnimalType.DOG) } throws RuntimeException("Server error")

        val viewModel = AnimalListViewModel(animalType = AnimalType.DOG, repository = repository)

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals("Server error", state.errorMessage)
    }
}
