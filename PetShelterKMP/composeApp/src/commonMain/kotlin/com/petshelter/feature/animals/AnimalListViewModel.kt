package com.petshelter.feature.animals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petshelter.core.data.AnimalRepository
import com.petshelter.core.model.Animal
import com.petshelter.core.model.AnimalSize
import com.petshelter.core.model.AnimalType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AgeFilter(
    val maxMonths: Int,
    val label: String,
) {
    UP_TO_6_MONTHS(6, "\u2264 6 months"),
    UP_TO_1_YEAR(12, "\u2264 1 year"),
    UP_TO_2_YEARS(24, "\u2264 2 years"),
    UP_TO_4_YEARS(48, "\u2264 4 years"),
    UP_TO_8_YEARS(96, "\u2264 8 years"),
    UP_TO_16_YEARS(192, "\u2264 16 years"),
}

data class AnimalListUiState(
    val isLoading: Boolean = true,
    val animals: List<Animal> = emptyList(),
    val filteredAnimals: List<Animal> = emptyList(),
    val availableBreeds: List<String> = emptyList(),
    val selectedSex: String? = null,
    val selectedSize: AnimalSize? = null,
    val selectedBreed: String? = null,
    val selectedAge: AgeFilter? = null,
    val searchQuery: String = "",
    val selectedAnimalType: AnimalType? = null,
    val errorMessage: String? = null,
)

class AnimalListViewModel(
    private val animalType: AnimalType?,
    private val repository: AnimalRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AnimalListUiState())
    val uiState: StateFlow<AnimalListUiState> = _uiState.asStateFlow()

    init {
        loadAnimals()
    }

    fun onSexFilterChanged(sex: String?) {
        _uiState.update { it.copy(selectedSex = sex) }
        applyFilters()
    }

    fun onSizeFilterChanged(size: AnimalSize?) {
        _uiState.update { it.copy(selectedSize = size) }
        applyFilters()
    }

    fun onBreedFilterChanged(breed: String?) {
        _uiState.update { it.copy(selectedBreed = breed) }
        applyFilters()
    }

    fun onAgeFilterChanged(age: AgeFilter?) {
        _uiState.update { it.copy(selectedAge = age) }
        applyFilters()
    }

    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        applyFilters()
    }

    fun onAnimalTypeFilterChanged(type: AnimalType?) {
        _uiState.update { it.copy(selectedAnimalType = type, selectedBreed = null) }
        applyFilters()
    }

    private fun loadAnimals() {
        viewModelScope.launch {
            try {
                val animals =
                    if (animalType != null) {
                        repository.getAnimals(animalType)
                    } else {
                        repository.getAllAnimals()
                    }
                val breeds = animals.map { it.breed }.distinct().sorted()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        animals = animals,
                        filteredAnimals = animals,
                        availableBreeds = breeds,
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message)
                }
            }
        }
    }

    private fun applyFilters() {
        _uiState.update { state ->
            val filtered =
                state.animals.filter { animal ->
                    matchesAnimalType(animal, state.selectedAnimalType) &&
                        matchesSex(animal, state.selectedSex) &&
                        matchesSize(animal, state.selectedSize) &&
                        matchesBreed(animal, state.selectedBreed) &&
                        matchesAge(animal, state.selectedAge) &&
                        matchesSearchQuery(animal, state.searchQuery)
                }
            val breeds =
                state.animals
                    .filter { matchesAnimalType(it, state.selectedAnimalType) }
                    .map { it.breed }
                    .distinct()
                    .sorted()
            state.copy(filteredAnimals = filtered, availableBreeds = breeds)
        }
    }

    private fun matchesSex(
        animal: Animal,
        sex: String?,
    ): Boolean = sex == null || animal.sex == sex

    private fun matchesSize(
        animal: Animal,
        size: AnimalSize?,
    ): Boolean = size == null || animal.size == size

    private fun matchesBreed(
        animal: Animal,
        breed: String?,
    ): Boolean = breed == null || animal.breed == breed

    private fun matchesAge(
        animal: Animal,
        age: AgeFilter?,
    ): Boolean {
        if (age == null) return true
        val months = animal.ageMonths ?: return false
        return months <= age.maxMonths
    }

    private fun matchesAnimalType(
        animal: Animal,
        type: AnimalType?,
    ): Boolean = type == null || animal.animalType == type

    private fun matchesSearchQuery(
        animal: Animal,
        query: String,
    ): Boolean {
        if (query.isBlank()) return true
        val lowerQuery = query.lowercase()
        return animal.name.lowercase().contains(lowerQuery) ||
            animal.breed.lowercase().contains(lowerQuery)
    }
}
