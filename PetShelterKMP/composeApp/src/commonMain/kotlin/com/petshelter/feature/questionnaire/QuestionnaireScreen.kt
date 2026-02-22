package com.petshelter.feature.questionnaire

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography

@Composable
fun QuestionnaireScreen(
    viewModel: QuestionnaireViewModel,
    onAnimalClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    QuestionnaireScreenContent(
        state = uiState,
        onAnimalPreferenceChanged = viewModel::onAnimalPreferenceChanged,
        onSizePreferenceChanged = viewModel::onSizePreferenceChanged,
        onWalkTimeChanged = viewModel::onWalkTimeChanged,
        onAloneTimeChanged = viewModel::onAloneTimeChanged,
        onActivityLevelChanged = viewModel::onActivityLevelChanged,
        onAffectionPreferenceChanged = viewModel::onAffectionPreferenceChanged,
        onHasOtherAnimalsChanged = viewModel::onHasOtherAnimalsChanged,
        onHasKidsChanged = viewModel::onHasKidsChanged,
        onWantsToTeachTricksChanged = viewModel::onWantsToTeachTricksChanged,
        onSpecialNeedsChanged = viewModel::onSpecialNeedsChanged,
        onSubmit = viewModel::submitQuestionnaire,
        onRetake = viewModel::resetQuestionnaire,
        onAnimalClick = onAnimalClick,
        modifier = modifier,
    )
}

@Composable
internal fun QuestionnaireScreenContent(
    state: QuestionnaireUiState,
    onAnimalPreferenceChanged: (AnimalPreference) -> Unit,
    onSizePreferenceChanged: (SizePreference) -> Unit,
    onWalkTimeChanged: (WalkTimePreference) -> Unit,
    onAloneTimeChanged: (AloneTimePreference) -> Unit,
    onActivityLevelChanged: (ActivityLevel) -> Unit,
    onAffectionPreferenceChanged: (AffectionPreference) -> Unit,
    onHasOtherAnimalsChanged: (Boolean) -> Unit,
    onHasKidsChanged: (Boolean) -> Unit,
    onWantsToTeachTricksChanged: (Boolean) -> Unit,
    onSpecialNeedsChanged: (SpecialNeedsPreference) -> Unit,
    onSubmit: () -> Unit,
    onRetake: () -> Unit,
    onAnimalClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        state.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator(color = PetShelterTheme.colors.Primary)
            }
        }
        state.errorMessage != null -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = state.errorMessage,
                    style = PetShelterTypography.Body,
                    color = PetShelterTheme.colors.Error,
                    textAlign = TextAlign.Center,
                )
            }
        }
        else -> {
            AnimatedContent(
                targetState = state.phase,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                modifier = modifier,
                label = "questionnaire-phase",
            ) { phase ->
                when (phase) {
                    QuestionnairePhase.FORM ->
                        QuestionnaireForm(
                            answers = state.answers,
                            onAnimalPreferenceChanged = onAnimalPreferenceChanged,
                            onSizePreferenceChanged = onSizePreferenceChanged,
                            onWalkTimeChanged = onWalkTimeChanged,
                            onAloneTimeChanged = onAloneTimeChanged,
                            onActivityLevelChanged = onActivityLevelChanged,
                            onAffectionPreferenceChanged = onAffectionPreferenceChanged,
                            onHasOtherAnimalsChanged = onHasOtherAnimalsChanged,
                            onHasKidsChanged = onHasKidsChanged,
                            onWantsToTeachTricksChanged = onWantsToTeachTricksChanged,
                            onSpecialNeedsChanged = onSpecialNeedsChanged,
                            onSubmit = onSubmit,
                        )
                    QuestionnairePhase.RESULTS ->
                        QuestionnaireResults(
                            matchedAnimals = state.matchedAnimals,
                            onAnimalClick = onAnimalClick,
                            onRetake = onRetake,
                        )
                }
            }
        }
    }
}

@Preview
@Composable
private fun QuestionnaireScreenFormPreview() {
    PetShelterTheme {
        QuestionnaireScreenContent(
            state = QuestionnaireUiState(isLoading = false),
            onAnimalPreferenceChanged = {},
            onSizePreferenceChanged = {},
            onWalkTimeChanged = {},
            onAloneTimeChanged = {},
            onActivityLevelChanged = {},
            onAffectionPreferenceChanged = {},
            onHasOtherAnimalsChanged = {},
            onHasKidsChanged = {},
            onWantsToTeachTricksChanged = {},
            onSpecialNeedsChanged = {},
            onSubmit = {},
            onRetake = {},
            onAnimalClick = {},
        )
    }
}

@Preview
@Composable
private fun QuestionnaireScreenLoadingPreview() {
    PetShelterTheme {
        QuestionnaireScreenContent(
            state = QuestionnaireUiState(isLoading = true),
            onAnimalPreferenceChanged = {},
            onSizePreferenceChanged = {},
            onWalkTimeChanged = {},
            onAloneTimeChanged = {},
            onActivityLevelChanged = {},
            onAffectionPreferenceChanged = {},
            onHasOtherAnimalsChanged = {},
            onHasKidsChanged = {},
            onWantsToTeachTricksChanged = {},
            onSpecialNeedsChanged = {},
            onSubmit = {},
            onRetake = {},
            onAnimalClick = {},
        )
    }
}
