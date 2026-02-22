package com.petshelter.feature.dogs

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
fun DogsToAdoptScreen(
    onAnimalClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel =
        koinViewModel<AnimalListViewModel>(
            key = "dogs",
            parameters = { parametersOf(AnimalType.DOG) },
        )
    AnimalListScreen(
        viewModel = viewModel,
        onAnimalClick = onAnimalClick,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun DogsToAdoptScreenPreview() {
    PetShelterTheme {
        DogsToAdoptScreen(onAnimalClick = {})
    }
}
