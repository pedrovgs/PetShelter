package com.petshelter.feature.questionnaire

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class QuestionnaireAnswersTest {
    @Test
    fun isComplete_allFieldsFilledForDog_returnsTrue() {
        val answers = completeAnswers(animalPreference = AnimalPreference.DOG)

        assertTrue(answers.isComplete)
    }

    @Test
    fun isComplete_allFieldsFilledForEither_returnsTrue() {
        val answers = completeAnswers(animalPreference = AnimalPreference.EITHER)

        assertTrue(answers.isComplete)
    }

    @Test
    fun isComplete_missingWalkTimeForDog_returnsFalse() {
        val answers =
            completeAnswers(
                animalPreference = AnimalPreference.DOG,
            ).copy(walkTime = null)

        assertFalse(answers.isComplete)
    }

    @Test
    fun isComplete_catPreferenceWithoutWalkTime_returnsTrue() {
        val answers =
            QuestionnaireAnswers(
                animalPreference = AnimalPreference.CAT,
                sizePreference = SizePreference.SMALL,
                walkTime = null,
                aloneTime = AloneTimePreference.HALF_DAY,
                activityLevel = ActivityLevel.MODERATE,
                affectionPreference = AffectionPreference.BALANCED,
                hasOtherAnimals = false,
                hasKids = false,
                wantsToTeachTricks = false,
                specialNeeds = SpecialNeedsPreference.NO,
            )

        assertTrue(answers.isComplete)
    }

    @Test
    fun isComplete_missingRequiredFieldForCat_returnsFalse() {
        val answers =
            QuestionnaireAnswers(
                animalPreference = AnimalPreference.CAT,
                sizePreference = SizePreference.SMALL,
                aloneTime = null,
                activityLevel = ActivityLevel.MODERATE,
                affectionPreference = AffectionPreference.BALANCED,
                hasOtherAnimals = false,
                hasKids = false,
                wantsToTeachTricks = false,
                specialNeeds = SpecialNeedsPreference.NO,
            )

        assertFalse(answers.isComplete)
    }

    @Test
    fun answeredCount_allFieldsFilledForDog_returnsTen() {
        val answers = completeAnswers(animalPreference = AnimalPreference.DOG)

        assertEquals(10, answers.answeredCount)
    }

    @Test
    fun answeredCount_catWithAllFieldsExceptWalkTime_returnsNine() {
        val answers =
            QuestionnaireAnswers(
                animalPreference = AnimalPreference.CAT,
                sizePreference = SizePreference.ANY,
                walkTime = null,
                aloneTime = AloneTimePreference.FEW_HOURS,
                activityLevel = ActivityLevel.HIGH,
                affectionPreference = AffectionPreference.AFFECTIONATE,
                hasOtherAnimals = true,
                hasKids = true,
                wantsToTeachTricks = true,
                specialNeeds = SpecialNeedsPreference.YES,
            )

        assertEquals(9, answers.answeredCount)
    }

    @Test
    fun answeredCount_noFieldsFilled_returnsZero() {
        val answers = QuestionnaireAnswers()

        assertEquals(0, answers.answeredCount)
    }

    @Test
    fun answeredCount_partiallyFilled_countsCorrectly() {
        val answers =
            QuestionnaireAnswers(
                animalPreference = AnimalPreference.DOG,
                sizePreference = SizePreference.MEDIUM,
                walkTime = WalkTimePreference.SHORT,
            )

        assertEquals(3, answers.answeredCount)
    }

    @Test
    fun totalQuestions_nonCatPreference_returnsTen() {
        assertEquals(10, QuestionnaireAnswers(animalPreference = AnimalPreference.DOG).totalQuestions)
        assertEquals(10, QuestionnaireAnswers(animalPreference = AnimalPreference.EITHER).totalQuestions)
        assertEquals(10, QuestionnaireAnswers().totalQuestions)
    }

    @Test
    fun totalQuestions_catPreference_returnsNine() {
        assertEquals(9, QuestionnaireAnswers(animalPreference = AnimalPreference.CAT).totalQuestions)
    }
}

private fun completeAnswers(animalPreference: AnimalPreference = AnimalPreference.DOG) =
    QuestionnaireAnswers(
        animalPreference = animalPreference,
        sizePreference = SizePreference.MEDIUM,
        walkTime = WalkTimePreference.MODERATE,
        aloneTime = AloneTimePreference.HALF_DAY,
        activityLevel = ActivityLevel.MODERATE,
        affectionPreference = AffectionPreference.BALANCED,
        hasOtherAnimals = false,
        hasKids = false,
        wantsToTeachTricks = false,
        specialNeeds = SpecialNeedsPreference.NO,
    )
