package com.petshelter.feature.adopt

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.feature.questionnaire.QuestionnaireScreen
import com.petshelter.feature.questionnaire.QuestionnaireViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AdoptScreen(
    onAnimalClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = koinViewModel<QuestionnaireViewModel>()
    QuestionnaireScreen(
        viewModel = viewModel,
        onAnimalClick = onAnimalClick,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun AdoptScreenPreview() {
    PetShelterTheme {
        AdoptScreen(onAnimalClick = {})
    }
}
