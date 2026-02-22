package com.petshelter.feature.questionnaire

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petshelter.core.model.Animal
import com.petshelter.core.repository.AnimalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuestionnaireViewModel(
    private val repository: AnimalRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuestionnaireUiState())
    val uiState: StateFlow<QuestionnaireUiState> = _uiState.asStateFlow()

    private var allAnimals: List<Animal> = emptyList()

    init {
        loadAnimals()
    }

    fun onAnimalPreferenceChanged(preference: AnimalPreference) {
        _uiState.update {
            val newAnswers = it.answers.copy(animalPreference = preference)
            // Clear walk time if switching to CAT
            val adjusted =
                if (preference == AnimalPreference.CAT) {
                    newAnswers.copy(walkTime = null)
                } else {
                    newAnswers
                }
            it.copy(answers = adjusted)
        }
    }

    fun onSizePreferenceChanged(preference: SizePreference) {
        _uiState.update { it.copy(answers = it.answers.copy(sizePreference = preference)) }
    }

    fun onWalkTimeChanged(preference: WalkTimePreference) {
        _uiState.update { it.copy(answers = it.answers.copy(walkTime = preference)) }
    }

    fun onAloneTimeChanged(preference: AloneTimePreference) {
        _uiState.update { it.copy(answers = it.answers.copy(aloneTime = preference)) }
    }

    fun onActivityLevelChanged(level: ActivityLevel) {
        _uiState.update { it.copy(answers = it.answers.copy(activityLevel = level)) }
    }

    fun onAffectionPreferenceChanged(preference: AffectionPreference) {
        _uiState.update { it.copy(answers = it.answers.copy(affectionPreference = preference)) }
    }

    fun onHasOtherAnimalsChanged(hasOtherAnimals: Boolean) {
        _uiState.update { it.copy(answers = it.answers.copy(hasOtherAnimals = hasOtherAnimals)) }
    }

    fun onHasKidsChanged(hasKids: Boolean) {
        _uiState.update { it.copy(answers = it.answers.copy(hasKids = hasKids)) }
    }

    fun onWantsToTeachTricksChanged(wantsToTeachTricks: Boolean) {
        _uiState.update { it.copy(answers = it.answers.copy(wantsToTeachTricks = wantsToTeachTricks)) }
    }

    fun onSpecialNeedsChanged(preference: SpecialNeedsPreference) {
        _uiState.update { it.copy(answers = it.answers.copy(specialNeeds = preference)) }
    }

    fun submitQuestionnaire() {
        val answers = _uiState.value.answers
        val matches = computeMatches(answers, allAnimals)
        _uiState.update {
            it.copy(
                phase = QuestionnairePhase.RESULTS,
                matchedAnimals = matches,
            )
        }
    }

    fun resetQuestionnaire() {
        _uiState.update {
            it.copy(
                phase = QuestionnairePhase.FORM,
                answers = QuestionnaireAnswers(),
                matchedAnimals = emptyList(),
            )
        }
    }

    private fun loadAnimals() {
        viewModelScope.launch {
            try {
                allAnimals = repository.getAllAnimals()
                _uiState.update { it.copy(isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }
}
