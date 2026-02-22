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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import com.petshelter.core.model.AnimalSex
import com.petshelter.core.model.AnimalSize
import com.petshelter.core.model.AnimalType
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Spacing
import com.petshelter.designsystem.icons.PetShelterIcons
import org.jetbrains.compose.resources.stringResource
import petshelter.composeapp.generated.resources.Res
import petshelter.composeapp.generated.resources.animals_empty_message
import petshelter.composeapp.generated.resources.animals_results_count
import petshelter.composeapp.generated.resources.close
import petshelter.composeapp.generated.resources.search_animals_placeholder

private val GRID_MIN_COLUMN_WIDTH = 160.dp

@Composable
fun AnimalListScreen(
    viewModel: AnimalListViewModel,
    onAnimalClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    showSearchBar: Boolean = false,
    showAnimalTypeFilter: Boolean = false,
) {
    val uiState by viewModel.uiState.collectAsState()

    AnimalListContent(
        state = uiState,
        onAnimalClick = onAnimalClick,
        onSexChanged = viewModel::onSexFilterChanged,
        onSizeChanged = viewModel::onSizeFilterChanged,
        onBreedChanged = viewModel::onBreedFilterChanged,
        onAgeChanged = viewModel::onAgeFilterChanged,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onAnimalTypeChanged = viewModel::onAnimalTypeFilterChanged,
        showSearchBar = showSearchBar,
        showAnimalTypeFilter = showAnimalTypeFilter,
        modifier = modifier,
    )
}

@Composable
internal fun AnimalListContent(
    state: AnimalListUiState,
    onAnimalClick: (String) -> Unit,
    onSexChanged: (AnimalSex?) -> Unit,
    onSizeChanged: (AnimalSize?) -> Unit,
    onBreedChanged: (String?) -> Unit,
    onAgeChanged: (AgeFilter?) -> Unit,
    onSearchQueryChanged: (String) -> Unit = {},
    onAnimalTypeChanged: (AnimalType?) -> Unit = {},
    showSearchBar: Boolean = false,
    showAnimalTypeFilter: Boolean = false,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Spacer(Modifier.height(Spacing.Medium))

        if (showSearchBar) {
            SearchBar(
                query = state.searchQuery,
                onQueryChanged = onSearchQueryChanged,
            )
            Spacer(Modifier.height(Spacing.Small))
        }

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
            showAnimalTypeFilter = showAnimalTypeFilter,
            selectedAnimalType = state.selectedAnimalType,
            onAnimalTypeChanged = onAnimalTypeChanged,
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
private fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit,
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = {
            Text(
                text = stringResource(Res.string.search_animals_placeholder),
                style = PetShelterTypography.Body,
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChanged("") }) {
                    Icon(
                        imageVector = PetShelterIcons.Close,
                        contentDescription = stringResource(Res.string.close),
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        },
        singleLine = true,
        textStyle = PetShelterTypography.Body,
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PetShelterTheme.colors.Primary,
                unfocusedBorderColor = PetShelterTheme.colors.BorderLight,
                cursorColor = PetShelterTheme.colors.Primary,
            ),
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.Medium),
    )
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
                    selectedSex = AnimalSex.FEMALE,
                ),
            onAnimalClick = {},
            onSexChanged = {},
            onSizeChanged = {},
            onBreedChanged = {},
            onAgeChanged = {},
        )
    }
}
