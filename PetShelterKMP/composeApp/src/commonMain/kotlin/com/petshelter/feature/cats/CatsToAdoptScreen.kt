package com.petshelter.feature.cats

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.petshelter.core.model.AnimalType
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.feature.animals.AnimalListScreen
import com.petshelter.feature.animals.AnimalListViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CatsToAdoptScreen(
    onAnimalClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel =
        koinViewModel<AnimalListViewModel>(
            key = "cats",
            parameters = { parametersOf(AnimalType.CAT) },
        )
    AnimalListScreen(
        viewModel = viewModel,
        onAnimalClick = onAnimalClick,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun CatsToAdoptScreenPreview() {
    PetShelterTheme {
        CatsToAdoptScreen(onAnimalClick = {})
    }
}
