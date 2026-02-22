package com.petshelter.feature.questionnaire

import com.petshelter.core.model.AnimalSize
import com.petshelter.core.model.AnimalType
import com.petshelter.core.repository.AnimalRepository
import com.petshelter.testAnimal
import com.petshelter.testAnimalScores
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
class QuestionnaireViewModelTest {
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
    fun `initial state is FORM phase with loading false after animals load`() {
        everySuspend { repository.getAllAnimals() } returns emptyList()

        val viewModel = QuestionnaireViewModel(repository = repository)

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(QuestionnairePhase.FORM, state.phase)
        assertEquals(QuestionnaireAnswers(), state.answers)
        assertTrue(state.matchedAnimals.isEmpty())
    }

    @Test
    fun `updates animal preference`() {
        everySuspend { repository.getAllAnimals() } returns emptyList()

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onAnimalPreferenceChanged(AnimalPreference.DOG)

        assertEquals(AnimalPreference.DOG, viewModel.uiState.value.answers.animalPreference)
    }

    @Test
    fun `changing animal preference to CAT clears walk time`() {
        everySuspend { repository.getAllAnimals() } returns emptyList()

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onAnimalPreferenceChanged(AnimalPreference.DOG)
        viewModel.onWalkTimeChanged(WalkTimePreference.LONG)
        assertEquals(WalkTimePreference.LONG, viewModel.uiState.value.answers.walkTime)

        viewModel.onAnimalPreferenceChanged(AnimalPreference.CAT)

        val answers = viewModel.uiState.value.answers
        assertEquals(AnimalPreference.CAT, answers.animalPreference)
        assertNull(answers.walkTime)
    }

    @Test
    fun `changing animal preference to DOG does not clear walk time`() {
        everySuspend { repository.getAllAnimals() } returns emptyList()

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onAnimalPreferenceChanged(AnimalPreference.DOG)
        viewModel.onWalkTimeChanged(WalkTimePreference.MODERATE)

        viewModel.onAnimalPreferenceChanged(AnimalPreference.EITHER)

        assertEquals(WalkTimePreference.MODERATE, viewModel.uiState.value.answers.walkTime)
    }

    @Test
    fun `updates size preference`() {
        everySuspend { repository.getAllAnimals() } returns emptyList()

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onSizePreferenceChanged(SizePreference.LARGE)

        assertEquals(SizePreference.LARGE, viewModel.uiState.value.answers.sizePreference)
    }

    @Test
    fun `updates walk time preference`() {
        everySuspend { repository.getAllAnimals() } returns emptyList()

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onWalkTimeChanged(WalkTimePreference.SHORT)

        assertEquals(WalkTimePreference.SHORT, viewModel.uiState.value.answers.walkTime)
    }

    @Test
    fun `updates alone time preference`() {
        everySuspend { repository.getAllAnimals() } returns emptyList()

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onAloneTimeChanged(AloneTimePreference.HALF_DAY)

        assertEquals(AloneTimePreference.HALF_DAY, viewModel.uiState.value.answers.aloneTime)
    }

    @Test
    fun `updates activity level`() {
        everySuspend { repository.getAllAnimals() } returns emptyList()

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onActivityLevelChanged(ActivityLevel.HIGH)

        assertEquals(ActivityLevel.HIGH, viewModel.uiState.value.answers.activityLevel)
    }

    @Test
    fun `updates affection preference`() {
        everySuspend { repository.getAllAnimals() } returns emptyList()

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onAffectionPreferenceChanged(AffectionPreference.AFFECTIONATE)

        assertEquals(AffectionPreference.AFFECTIONATE, viewModel.uiState.value.answers.affectionPreference)
    }

    @Test
    fun `updates has other animals`() {
        everySuspend { repository.getAllAnimals() } returns emptyList()

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onHasOtherAnimalsChanged(true)

        assertEquals(true, viewModel.uiState.value.answers.hasOtherAnimals)
    }

    @Test
    fun `updates has kids`() {
        everySuspend { repository.getAllAnimals() } returns emptyList()

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onHasKidsChanged(true)

        assertEquals(true, viewModel.uiState.value.answers.hasKids)
    }

    @Test
    fun `updates wants to teach tricks`() {
        everySuspend { repository.getAllAnimals() } returns emptyList()

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onWantsToTeachTricksChanged(true)

        assertEquals(true, viewModel.uiState.value.answers.wantsToTeachTricks)
    }

    @Test
    fun `updates special needs preference`() {
        everySuspend { repository.getAllAnimals() } returns emptyList()

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onSpecialNeedsChanged(SpecialNeedsPreference.YES)

        assertEquals(SpecialNeedsPreference.YES, viewModel.uiState.value.answers.specialNeeds)
    }

    @Test
    fun `submit questionnaire transitions to RESULTS phase with matched animals`() {
        val animals =
            listOf(
                testAnimal(
                    id = "1",
                    name = "Luna",
                    animalType = AnimalType.DOG,
                    size = AnimalSize.MEDIUM,
                    scores = testAnimalScores(energy = 5, activity = 5, friendly = 8),
                ),
                testAnimal(
                    id = "2",
                    name = "Whiskers",
                    animalType = AnimalType.CAT,
                    size = AnimalSize.SMALL,
                    scores = testAnimalScores(energy = 3, activity = 3, friendly = 6),
                ),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onAnimalPreferenceChanged(AnimalPreference.DOG)
        viewModel.onSizePreferenceChanged(SizePreference.ANY)
        viewModel.onWalkTimeChanged(WalkTimePreference.MODERATE)
        viewModel.onAloneTimeChanged(AloneTimePreference.FEW_HOURS)
        viewModel.onActivityLevelChanged(ActivityLevel.MODERATE)
        viewModel.onAffectionPreferenceChanged(AffectionPreference.AFFECTIONATE)
        viewModel.onHasOtherAnimalsChanged(false)
        viewModel.onHasKidsChanged(false)
        viewModel.onWantsToTeachTricksChanged(true)
        viewModel.onSpecialNeedsChanged(SpecialNeedsPreference.NO)

        viewModel.submitQuestionnaire()

        val state = viewModel.uiState.value
        assertEquals(QuestionnairePhase.RESULTS, state.phase)
        assertTrue(state.matchedAnimals.isNotEmpty())
        assertTrue(state.matchedAnimals.all { it.animal.animalType == AnimalType.DOG })
    }

    @Test
    fun `submit questionnaire with EITHER preference returns both types`() {
        val animals =
            listOf(
                testAnimal(id = "1", animalType = AnimalType.DOG, size = AnimalSize.MEDIUM),
                testAnimal(id = "2", animalType = AnimalType.CAT, size = AnimalSize.MEDIUM),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onAnimalPreferenceChanged(AnimalPreference.EITHER)
        viewModel.onSizePreferenceChanged(SizePreference.ANY)
        viewModel.onWalkTimeChanged(WalkTimePreference.MODERATE)
        viewModel.onAloneTimeChanged(AloneTimePreference.FEW_HOURS)
        viewModel.onActivityLevelChanged(ActivityLevel.MODERATE)
        viewModel.onAffectionPreferenceChanged(AffectionPreference.BALANCED)
        viewModel.onHasOtherAnimalsChanged(false)
        viewModel.onHasKidsChanged(false)
        viewModel.onWantsToTeachTricksChanged(false)
        viewModel.onSpecialNeedsChanged(SpecialNeedsPreference.MAYBE)

        viewModel.submitQuestionnaire()

        val matchedTypes =
            viewModel.uiState.value.matchedAnimals
                .map { it.animal.animalType }
                .toSet()
        assertEquals(setOf(AnimalType.DOG, AnimalType.CAT), matchedTypes)
    }

    @Test
    fun `matched animals are sorted by match percentage descending`() {
        val animals =
            listOf(
                testAnimal(
                    id = "1",
                    animalType = AnimalType.DOG,
                    size = AnimalSize.MEDIUM,
                    scores =
                        testAnimalScores(
                            energy = 2,
                            activity = 2,
                            friendly = 3,
                            goodWithAnimals = 2,
                            goodWithHumans = 3,
                            trainability = 2,
                            dailyActivityRequirement = 2,
                            specialNeeds = 8,
                        ),
                ),
                testAnimal(
                    id = "2",
                    animalType = AnimalType.DOG,
                    size = AnimalSize.MEDIUM,
                    scores =
                        testAnimalScores(
                            energy = 6,
                            activity = 5,
                            friendly = 9,
                            goodWithAnimals = 8,
                            goodWithHumans = 9,
                            trainability = 9,
                            dailyActivityRequirement = 5,
                            specialNeeds = 0,
                        ),
                ),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onAnimalPreferenceChanged(AnimalPreference.DOG)
        viewModel.onSizePreferenceChanged(SizePreference.ANY)
        viewModel.onWalkTimeChanged(WalkTimePreference.MODERATE)
        viewModel.onAloneTimeChanged(AloneTimePreference.FEW_HOURS)
        viewModel.onActivityLevelChanged(ActivityLevel.MODERATE)
        viewModel.onAffectionPreferenceChanged(AffectionPreference.AFFECTIONATE)
        viewModel.onHasOtherAnimalsChanged(true)
        viewModel.onHasKidsChanged(true)
        viewModel.onWantsToTeachTricksChanged(true)
        viewModel.onSpecialNeedsChanged(SpecialNeedsPreference.NO)

        viewModel.submitQuestionnaire()

        val matches = viewModel.uiState.value.matchedAnimals
        assertEquals(2, matches.size)
        assertTrue(matches[0].matchPercentage >= matches[1].matchPercentage)
        assertEquals("2", matches[0].animal.id)
    }

    @Test
    fun `reset questionnaire returns to FORM phase with empty answers`() {
        everySuspend { repository.getAllAnimals() } returns
            listOf(
                testAnimal(id = "1", animalType = AnimalType.DOG, size = AnimalSize.MEDIUM),
            )

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onAnimalPreferenceChanged(AnimalPreference.DOG)
        viewModel.onSizePreferenceChanged(SizePreference.LARGE)
        viewModel.onWalkTimeChanged(WalkTimePreference.LONG)
        viewModel.onAloneTimeChanged(AloneTimePreference.FEW_HOURS)
        viewModel.onActivityLevelChanged(ActivityLevel.HIGH)
        viewModel.onAffectionPreferenceChanged(AffectionPreference.AFFECTIONATE)
        viewModel.onHasOtherAnimalsChanged(false)
        viewModel.onHasKidsChanged(false)
        viewModel.onWantsToTeachTricksChanged(true)
        viewModel.onSpecialNeedsChanged(SpecialNeedsPreference.NO)
        viewModel.submitQuestionnaire()
        assertEquals(QuestionnairePhase.RESULTS, viewModel.uiState.value.phase)

        viewModel.resetQuestionnaire()

        val state = viewModel.uiState.value
        assertEquals(QuestionnairePhase.FORM, state.phase)
        assertEquals(QuestionnaireAnswers(), state.answers)
        assertTrue(state.matchedAnimals.isEmpty())
    }

    @Test
    fun `handles repository error on load`() {
        everySuspend { repository.getAllAnimals() } throws RuntimeException("Connection timeout")

        val viewModel = QuestionnaireViewModel(repository = repository)

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals("Connection timeout", state.errorMessage)
    }

    @Test
    fun `submit with no animals returns empty results`() {
        everySuspend { repository.getAllAnimals() } returns emptyList()

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onAnimalPreferenceChanged(AnimalPreference.DOG)
        viewModel.submitQuestionnaire()

        val state = viewModel.uiState.value
        assertEquals(QuestionnairePhase.RESULTS, state.phase)
        assertTrue(state.matchedAnimals.isEmpty())
    }

    @Test
    fun `size preference filter excludes non-matching sizes`() {
        val animals =
            listOf(
                testAnimal(id = "1", animalType = AnimalType.DOG, size = AnimalSize.SMALL),
                testAnimal(id = "2", animalType = AnimalType.DOG, size = AnimalSize.LARGE),
            )
        everySuspend { repository.getAllAnimals() } returns animals

        val viewModel = QuestionnaireViewModel(repository = repository)
        viewModel.onAnimalPreferenceChanged(AnimalPreference.DOG)
        viewModel.onSizePreferenceChanged(SizePreference.SMALL)
        viewModel.onWalkTimeChanged(WalkTimePreference.SHORT)
        viewModel.onAloneTimeChanged(AloneTimePreference.HALF_DAY)
        viewModel.onActivityLevelChanged(ActivityLevel.LOW)
        viewModel.onAffectionPreferenceChanged(AffectionPreference.BALANCED)
        viewModel.onHasOtherAnimalsChanged(false)
        viewModel.onHasKidsChanged(false)
        viewModel.onWantsToTeachTricksChanged(false)
        viewModel.onSpecialNeedsChanged(SpecialNeedsPreference.MAYBE)

        viewModel.submitQuestionnaire()

        val matches = viewModel.uiState.value.matchedAnimals
        assertEquals(1, matches.size)
        assertEquals(AnimalSize.SMALL, matches.first().animal.size)
    }
}
