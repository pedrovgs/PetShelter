package com.petshelter.feature.animals

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.petshelter.core.model.Animal
import com.petshelter.core.model.AnimalSize
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Spacing
import org.jetbrains.compose.resources.stringResource
import petshelter.composeapp.generated.resources.Res
import petshelter.composeapp.generated.resources.animals_empty_message
import petshelter.composeapp.generated.resources.animals_results_count

private val GRID_MIN_COLUMN_WIDTH = 160.dp

@Composable
fun AnimalListScreen(
    viewModel: AnimalListViewModel,
    onAnimalClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    AnimalListContent(
        state = uiState,
        onAnimalClick = onAnimalClick,
        onSexChanged = viewModel::onSexFilterChanged,
        onSizeChanged = viewModel::onSizeFilterChanged,
        onBreedChanged = viewModel::onBreedFilterChanged,
        onAgeChanged = viewModel::onAgeFilterChanged,
        modifier = modifier,
    )
}

@Composable
internal fun AnimalListContent(
    state: AnimalListUiState,
    onAnimalClick: (String) -> Unit,
    onSexChanged: (String?) -> Unit,
    onSizeChanged: (AnimalSize?) -> Unit,
    onBreedChanged: (String?) -> Unit,
    onAgeChanged: (AgeFilter?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Spacer(Modifier.height(Spacing.Medium))

        FilterBar(
            selectedSex = state.selectedSex,
            selectedSize = state.selectedSize,
            selectedBreed = state.selectedBreed,
            selectedAge = state.selectedAge,
            availableBreeds = state.availableBreeds,
            onSexChanged = onSexChanged,
            onSizeChanged = onSizeChanged,
            onBreedChanged = onBreedChanged,
            onAgeChanged = onAgeChanged,
        )

        Spacer(Modifier.height(Spacing.Small))

        ResultsCount(count = state.filteredAnimals.size)

        when {
            state.isLoading -> LoadingState()
            state.errorMessage != null -> ErrorState(message = state.errorMessage)
            state.filteredAnimals.isEmpty() -> EmptyState()
            else ->
                AnimalGrid(
                    animals = state.filteredAnimals,
                    onAnimalClick = onAnimalClick,
                )
        }
    }
}

@Composable
private fun ResultsCount(count: Int) {
    Text(
        text = stringResource(Res.string.animals_results_count, count),
        style = PetShelterTypography.Caption,
        color = PetShelterTheme.colors.TextTertiary,
        modifier = Modifier.padding(horizontal = Spacing.Large),
    )
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = PetShelterTheme.colors.Primary)
    }
}

@Composable
private fun ErrorState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize().padding(Spacing.Large),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = message,
            style = PetShelterTypography.Body,
            color = PetShelterTheme.colors.Error,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun EmptyState() {
    Box(
        modifier = Modifier.fillMaxSize().padding(Spacing.Large),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "\uD83D\uDD0D",
                style = PetShelterTypography.Heading1,
            )
            Spacer(Modifier.height(Spacing.Medium))
            Text(
                text = stringResource(Res.string.animals_empty_message),
                style = PetShelterTypography.Body,
                color = PetShelterTheme.colors.TextSecondary,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun AnimalGrid(
    animals: List<Animal>,
    onAnimalClick: (String) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = GRID_MIN_COLUMN_WIDTH),
        contentPadding = PaddingValues(Spacing.Medium),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Medium),
        verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(
            items = animals,
            key = { it.id },
        ) { animal ->
            AnimalCard(
                animal = animal,
                onClick = { onAnimalClick(animal.id) },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview
@Composable
private fun AnimalListContentLoadingPreview() {
    PetShelterTheme {
        AnimalListContent(
            state = AnimalListUiState(isLoading = true),
            onAnimalClick = {},
            onSexChanged = {},
            onSizeChanged = {},
            onBreedChanged = {},
            onAgeChanged = {},
        )
    }
}

@Preview
@Composable
private fun AnimalListContentPreview() {
    PetShelterTheme {
        AnimalListContent(
            state =
                AnimalListUiState(
                    isLoading = false,
                    filteredAnimals = listOf(previewAnimal(), previewAnimal().copy(id = "2", name = "Max")),
                    availableBreeds = listOf("Malinois", "Mestizo"),
                ),
            onAnimalClick = {},
            onSexChanged = {},
            onSizeChanged = {},
            onBreedChanged = {},
            onAgeChanged = {},
        )
    }
}

@Preview
@Composable
private fun AnimalListContentEmptyPreview() {
    PetShelterTheme {
        AnimalListContent(
            state =
                AnimalListUiState(
                    isLoading = false,
                    filteredAnimals = emptyList(),
                    availableBreeds = listOf("Malinois"),
                    selectedSex = "Hembra",
                ),
            onAnimalClick = {},
            onSexChanged = {},
            onSizeChanged = {},
            onBreedChanged = {},
            onAgeChanged = {},
        )
    }
}
