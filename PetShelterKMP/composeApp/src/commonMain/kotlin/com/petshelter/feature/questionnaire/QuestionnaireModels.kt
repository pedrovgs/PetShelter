package com.petshelter.feature.questionnaire

import com.petshelter.core.model.Animal

enum class AnimalPreference { CAT, DOG, EITHER }

enum class SizePreference { SMALL, MEDIUM, LARGE, ANY }

enum class WalkTimePreference { SHORT, MODERATE, LONG }

enum class AloneTimePreference { FEW_HOURS, HALF_DAY, FULL_DAY }

enum class ActivityLevel { LOW, MODERATE, HIGH }

enum class AffectionPreference { SHY, BALANCED, AFFECTIONATE }

enum class SpecialNeedsPreference { YES, MAYBE, NO }

data class QuestionnaireAnswers(
    val animalPreference: AnimalPreference? = null,
    val sizePreference: SizePreference? = null,
    val walkTime: WalkTimePreference? = null,
    val aloneTime: AloneTimePreference? = null,
    val activityLevel: ActivityLevel? = null,
    val affectionPreference: AffectionPreference? = null,
    val hasOtherAnimals: Boolean? = null,
    val hasKids: Boolean? = null,
    val wantsToTeachTricks: Boolean? = null,
    val specialNeeds: SpecialNeedsPreference? = null,
) {
    val isComplete: Boolean
        get() {
            val walkTimeRequired = animalPreference != AnimalPreference.CAT
            return animalPreference != null &&
                sizePreference != null &&
                (!walkTimeRequired || walkTime != null) &&
                aloneTime != null &&
                activityLevel != null &&
                affectionPreference != null &&
                hasOtherAnimals != null &&
                hasKids != null &&
                wantsToTeachTricks != null &&
                specialNeeds != null
        }

    val answeredCount: Int
        get() {
            var count = 0
            if (animalPreference != null) count++
            if (sizePreference != null) count++
            if (animalPreference != AnimalPreference.CAT && walkTime != null) count++
            if (aloneTime != null) count++
            if (activityLevel != null) count++
            if (affectionPreference != null) count++
            if (hasOtherAnimals != null) count++
            if (hasKids != null) count++
            if (wantsToTeachTricks != null) count++
            if (specialNeeds != null) count++
            return count
        }

    val totalQuestions: Int
        get() = if (animalPreference == AnimalPreference.CAT) 9 else 10
}

data class MatchedAnimal(
    val animal: Animal,
    val matchPercentage: Int,
)

enum class QuestionnairePhase { FORM, RESULTS }

data class QuestionnaireUiState(
    val isLoading: Boolean = true,
    val phase: QuestionnairePhase = QuestionnairePhase.FORM,
    val answers: QuestionnaireAnswers = QuestionnaireAnswers(),
    val matchedAnimals: List<MatchedAnimal> = emptyList(),
    val errorMessage: String? = null,
)
