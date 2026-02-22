package com.petshelter.core.usecase

import com.petshelter.core.model.Animal
import com.petshelter.core.model.AnimalSize
import com.petshelter.core.model.AnimalType
import com.petshelter.feature.questionnaire.ActivityLevel
import com.petshelter.feature.questionnaire.AffectionPreference
import com.petshelter.feature.questionnaire.AloneTimePreference
import com.petshelter.feature.questionnaire.AnimalPreference
import com.petshelter.feature.questionnaire.MatchedAnimal
import com.petshelter.feature.questionnaire.QuestionnaireAnswers
import com.petshelter.feature.questionnaire.SizePreference
import com.petshelter.feature.questionnaire.SpecialNeedsPreference
import com.petshelter.feature.questionnaire.WalkTimePreference
import kotlin.math.abs
import kotlin.math.roundToInt

class MatchAnimalsUseCase {
    operator fun invoke(
        answers: QuestionnaireAnswers,
        animals: List<Animal>,
    ): List<MatchedAnimal> {
        val filtered = animals.filter { passesHardFilters(answers, it) }
        return filtered
            .map { animal ->
                MatchedAnimal(
                    animal = animal,
                    matchPercentage = computeScore(answers, animal),
                )
            }.sortedByDescending { it.matchPercentage }
    }
}

internal fun passesHardFilters(
    answers: QuestionnaireAnswers,
    animal: Animal,
): Boolean {
    when (answers.animalPreference) {
        AnimalPreference.DOG -> if (animal.animalType != AnimalType.DOG) return false
        AnimalPreference.CAT -> if (animal.animalType != AnimalType.CAT) return false
        AnimalPreference.EITHER, null -> {}
    }

    when (answers.sizePreference) {
        SizePreference.SMALL -> if (animal.size != AnimalSize.SMALL) return false
        SizePreference.MEDIUM -> if (animal.size != AnimalSize.MEDIUM) return false
        SizePreference.LARGE ->
            if (animal.size != AnimalSize.LARGE && animal.size != AnimalSize.EXTRA_LARGE) return false
        SizePreference.ANY, null -> {}
    }

    return true
}

internal fun computeScore(
    answers: QuestionnaireAnswers,
    animal: Animal,
): Int {
    val isDog = animal.animalType == AnimalType.DOG
    val scores = animal.scores

    data class WeightedScore(
        val score: Double,
        val weight: Int,
    )

    val components = mutableListOf<WeightedScore>()

    if (isDog && answers.walkTime != null) {
        val target =
            when (answers.walkTime) {
                WalkTimePreference.SHORT -> 3.0
                WalkTimePreference.MODERATE -> 5.0
                WalkTimePreference.LONG -> 8.0
            }
        val raw = (10.0 - abs(target - scores.dailyActivityRequirement)).coerceIn(0.0, 10.0)
        components.add(WeightedScore(raw / 10.0, 15))
    }

    if (answers.aloneTime != null) {
        val raw =
            when (answers.aloneTime) {
                AloneTimePreference.FULL_DAY -> (10.0 - scores.energy).coerceIn(0.0, 10.0)
                AloneTimePreference.HALF_DAY -> {
                    val mid = 5.0
                    (10.0 - abs(mid - scores.energy)).coerceIn(0.0, 10.0)
                }
                AloneTimePreference.FEW_HOURS -> scores.energy.toDouble()
            }
        components.add(WeightedScore(raw / 10.0, 12))
    }

    if (answers.activityLevel != null) {
        val target =
            when (answers.activityLevel) {
                ActivityLevel.LOW -> 3.0
                ActivityLevel.MODERATE -> 5.5
                ActivityLevel.HIGH -> 8.5
            }
        val avg = (scores.energy + scores.activity) / 2.0
        val raw = (10.0 - abs(target - avg)).coerceIn(0.0, 10.0)
        components.add(WeightedScore(raw / 10.0, 15))
    }

    if (answers.affectionPreference != null) {
        val raw =
            when (answers.affectionPreference) {
                AffectionPreference.AFFECTIONATE -> scores.friendly.toDouble()
                AffectionPreference.SHY -> scores.shy.toDouble()
                AffectionPreference.BALANCED -> (scores.friendly + scores.shy) / 2.0
            }
        components.add(WeightedScore(raw / 10.0, 12))
    }

    if (answers.hasOtherAnimals != null) {
        val raw =
            if (answers.hasOtherAnimals) {
                scores.goodWithAnimals.toDouble()
            } else {
                10.0
            }
        components.add(WeightedScore(raw / 10.0, 12))
    }

    if (answers.hasKids != null) {
        val raw =
            if (answers.hasKids) {
                (scores.goodWithHumans + scores.friendly) / 2.0
            } else {
                10.0
            }
        components.add(WeightedScore(raw / 10.0, 12))
    }

    if (answers.wantsToTeachTricks != null) {
        val raw =
            if (answers.wantsToTeachTricks) {
                scores.trainability.toDouble()
            } else {
                10.0
            }
        components.add(WeightedScore(raw / 10.0, 10))
    }

    if (answers.specialNeeds != null) {
        val raw =
            when (answers.specialNeeds) {
                SpecialNeedsPreference.YES -> 10.0
                SpecialNeedsPreference.MAYBE -> (10.0 - scores.specialNeeds / 2.0).coerceIn(0.0, 10.0)
                SpecialNeedsPreference.NO -> (10.0 - scores.specialNeeds).coerceIn(0.0, 10.0)
            }
        components.add(WeightedScore(raw / 10.0, 12))
    }

    if (components.isEmpty()) return 50

    val totalWeight = components.sumOf { it.weight }
    val weightedSum = components.sumOf { it.score * it.weight }
    return (weightedSum / totalWeight * 100).roundToInt().coerceIn(0, 100)
}
