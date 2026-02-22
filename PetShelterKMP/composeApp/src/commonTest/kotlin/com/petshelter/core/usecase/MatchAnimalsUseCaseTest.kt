package com.petshelter.core.usecase

import com.petshelter.core.model.Animal
import com.petshelter.core.model.AnimalScores
import com.petshelter.core.model.AnimalSex
import com.petshelter.core.model.AnimalSize
import com.petshelter.core.model.AnimalType
import com.petshelter.feature.questionnaire.ActivityLevel
import com.petshelter.feature.questionnaire.AnimalPreference
import com.petshelter.feature.questionnaire.QuestionnaireAnswers
import com.petshelter.feature.questionnaire.SizePreference
import com.petshelter.feature.questionnaire.SpecialNeedsPreference
import com.petshelter.feature.questionnaire.WalkTimePreference
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MatchAnimalsUseCaseTest {
    private val useCase = MatchAnimalsUseCase()

    @Test
    fun hardFilter_dogPreference_filtersCats() {
        val answers = QuestionnaireAnswers(animalPreference = AnimalPreference.DOG)
        val cat = testAnimal(animalType = AnimalType.CAT)

        assertFalse(passesHardFilters(answers, cat))
    }

    @Test
    fun hardFilter_dogPreference_passesDogs() {
        val answers = QuestionnaireAnswers(animalPreference = AnimalPreference.DOG)
        val dog = testAnimal(animalType = AnimalType.DOG)

        assertTrue(passesHardFilters(answers, dog))
    }

    @Test
    fun hardFilter_catPreference_filtersDogs() {
        val answers = QuestionnaireAnswers(animalPreference = AnimalPreference.CAT)
        val dog = testAnimal(animalType = AnimalType.DOG)

        assertFalse(passesHardFilters(answers, dog))
    }

    @Test
    fun hardFilter_catPreference_passesCats() {
        val answers = QuestionnaireAnswers(animalPreference = AnimalPreference.CAT)
        val cat = testAnimal(animalType = AnimalType.CAT)

        assertTrue(passesHardFilters(answers, cat))
    }

    @Test
    fun hardFilter_eitherPreference_passesAllAnimalTypes() {
        val answers = QuestionnaireAnswers(animalPreference = AnimalPreference.EITHER)
        val dog = testAnimal(animalType = AnimalType.DOG)
        val cat = testAnimal(animalType = AnimalType.CAT)

        assertTrue(passesHardFilters(answers, dog))
        assertTrue(passesHardFilters(answers, cat))
    }

    @Test
    fun hardFilter_smallSizePreference_passesOnlySmallAnimals() {
        val answers = QuestionnaireAnswers(sizePreference = SizePreference.SMALL)

        assertTrue(passesHardFilters(answers, testAnimal(size = AnimalSize.SMALL)))
        assertFalse(passesHardFilters(answers, testAnimal(size = AnimalSize.MEDIUM)))
        assertFalse(passesHardFilters(answers, testAnimal(size = AnimalSize.LARGE)))
        assertFalse(passesHardFilters(answers, testAnimal(size = AnimalSize.EXTRA_LARGE)))
    }

    @Test
    fun hardFilter_largeSizePreference_passesLargeAndExtraLarge() {
        val answers = QuestionnaireAnswers(sizePreference = SizePreference.LARGE)

        assertFalse(passesHardFilters(answers, testAnimal(size = AnimalSize.SMALL)))
        assertFalse(passesHardFilters(answers, testAnimal(size = AnimalSize.MEDIUM)))
        assertTrue(passesHardFilters(answers, testAnimal(size = AnimalSize.LARGE)))
        assertTrue(passesHardFilters(answers, testAnimal(size = AnimalSize.EXTRA_LARGE)))
    }

    @Test
    fun hardFilter_anySizePreference_passesAllSizes() {
        val answers = QuestionnaireAnswers(sizePreference = SizePreference.ANY)

        AnimalSize.entries.forEach { size ->
            assertTrue(
                passesHardFilters(answers, testAnimal(size = size)),
                "Expected ANY size preference to pass $size",
            )
        }
    }

    @Test
    fun score_emptyAnswers_returnsFifty() {
        val answers = QuestionnaireAnswers()
        val animal = testAnimal()

        assertEquals(50, computeScore(answers, animal))
    }

    @Test
    fun score_highEnergyDogWithHighActivity_returnsHighScore() {
        val answers =
            QuestionnaireAnswers(
                activityLevel = ActivityLevel.HIGH,
            )
        val highEnergyDog =
            testAnimal(
                animalType = AnimalType.DOG,
                scores = testScores(energy = 9, activity = 8),
            )

        val score = computeScore(answers, highEnergyDog)
        assertTrue(score >= 80, "Expected high score for high energy dog + HIGH activity, got $score")
    }

    @Test
    fun score_lowEnergyDogWithLowActivity_returnsHighScore() {
        val answers =
            QuestionnaireAnswers(
                activityLevel = ActivityLevel.LOW,
            )
        val lowEnergyDog =
            testAnimal(
                animalType = AnimalType.DOG,
                scores = testScores(energy = 3, activity = 3),
            )

        val score = computeScore(answers, lowEnergyDog)
        assertTrue(score >= 80, "Expected high score for low energy dog + LOW activity, got $score")
    }

    @Test
    fun score_walkTimeIgnoredForCats() {
        val answersWithWalk =
            QuestionnaireAnswers(
                walkTime = WalkTimePreference.LONG,
            )
        val cat =
            testAnimal(
                animalType = AnimalType.CAT,
                scores = testScores(dailyActivityRequirement = 2),
            )

        val scoreWithWalk = computeScore(answersWithWalk, cat)
        val scoreWithoutWalk = computeScore(QuestionnaireAnswers(), cat)

        assertEquals(scoreWithoutWalk, scoreWithWalk)
    }

    @Test
    fun score_hasOtherAnimalsTrue_lowGoodWithAnimals_lowerScore() {
        val withAnimals = QuestionnaireAnswers(hasOtherAnimals = true)
        val withoutAnimals = QuestionnaireAnswers(hasOtherAnimals = false)

        val animal = testAnimal(scores = testScores(goodWithAnimals = 2))

        val scoreWith = computeScore(withAnimals, animal)
        val scoreWithout = computeScore(withoutAnimals, animal)

        assertTrue(
            scoreWith < scoreWithout,
            "Expected lower score when hasOtherAnimals=true and goodWithAnimals is low. " +
                "Got with=$scoreWith, without=$scoreWithout",
        )
    }

    @Test
    fun score_hasKidsTrue_highFriendlyAndGoodWithHumans_highScore() {
        val answers = QuestionnaireAnswers(hasKids = true)
        val friendlyAnimal =
            testAnimal(
                scores = testScores(friendly = 9, goodWithHumans = 9),
            )

        val score = computeScore(answers, friendlyAnimal)
        assertTrue(score >= 80, "Expected high score for kid-friendly animal, got $score")
    }

    @Test
    fun score_specialNeedsNo_highSpecialNeedsAnimal_lowerScore() {
        val noSpecialNeeds = QuestionnaireAnswers(specialNeeds = SpecialNeedsPreference.NO)
        val yesSpecialNeeds = QuestionnaireAnswers(specialNeeds = SpecialNeedsPreference.YES)

        val animal = testAnimal(scores = testScores(specialNeeds = 9))

        val scoreNo = computeScore(noSpecialNeeds, animal)
        val scoreYes = computeScore(yesSpecialNeeds, animal)

        assertTrue(
            scoreNo < scoreYes,
            "Expected lower score when specialNeeds=NO for high-needs animal. " +
                "Got no=$scoreNo, yes=$scoreYes",
        )
    }

    @Test
    fun invoke_resultsSortedByMatchPercentageDescending() {
        val answers =
            QuestionnaireAnswers(
                animalPreference = AnimalPreference.DOG,
                activityLevel = ActivityLevel.HIGH,
            )
        val lowEnergy = testAnimal(id = "low", scores = testScores(energy = 1, activity = 1))
        val midEnergy = testAnimal(id = "mid", scores = testScores(energy = 5, activity = 5))
        val highEnergy = testAnimal(id = "high", scores = testScores(energy = 9, activity = 9))
        val animals = listOf(lowEnergy, midEnergy, highEnergy)

        val results = useCase(answers, animals)

        assertEquals(3, results.size)
        assertTrue(
            results[0].matchPercentage >= results[1].matchPercentage,
            "First result should have highest or equal percentage",
        )
        assertTrue(
            results[1].matchPercentage >= results[2].matchPercentage,
            "Second result should have higher or equal percentage than third",
        )
        assertEquals("high", results.first().animal.id)
    }

    @Test
    fun invoke_emptyAnimalList_returnsEmptyResults() {
        val answers =
            QuestionnaireAnswers(
                animalPreference = AnimalPreference.DOG,
                activityLevel = ActivityLevel.HIGH,
            )

        val results = useCase(answers, emptyList())

        assertTrue(results.isEmpty())
    }
}

private fun testAnimal(
    id: String = "test-animal-1",
    animalType: AnimalType = AnimalType.DOG,
    name: String = "TestAnimal",
    sex: AnimalSex = AnimalSex.FEMALE,
    breed: String = "Mixed",
    size: AnimalSize = AnimalSize.MEDIUM,
    ageMonths: Int? = 24,
    description: String = "A friendly test animal.",
    images: List<String> = emptyList(),
    videos: List<String> = emptyList(),
    sourceUrl: String = "https://example.com/test",
    scores: AnimalScores = testScores(),
): Animal =
    Animal(
        id = id,
        animalType = animalType,
        name = name,
        sex = sex,
        breed = breed,
        size = size,
        ageMonths = ageMonths,
        description = description,
        images = images,
        videos = videos,
        sourceUrl = sourceUrl,
        scores = scores,
    )

private fun testScores(
    friendly: Int = 5,
    goodWithAnimals: Int = 5,
    leashTrained: Int = 5,
    reactive: Int = 5,
    specialNeeds: Int = 2,
    energy: Int = 5,
    goodWithHumans: Int = 5,
    shy: Int = 5,
    activity: Int = 5,
    trainability: Int = 5,
    dailyActivityRequirement: Int = 5,
): AnimalScores =
    AnimalScores(
        friendly = friendly,
        goodWithAnimals = goodWithAnimals,
        leashTrained = leashTrained,
        reactive = reactive,
        specialNeeds = specialNeeds,
        energy = energy,
        goodWithHumans = goodWithHumans,
        shy = shy,
        activity = activity,
        trainability = trainability,
        dailyActivityRequirement = dailyActivityRequirement,
    )
