package com.petshelter.feature.questionnaire

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.petshelter.components.AppButton
import com.petshelter.components.ButtonSize
import com.petshelter.components.ButtonVariant
import com.petshelter.core.model.Animal
import com.petshelter.core.model.AnimalScores
import com.petshelter.core.model.AnimalSize
import com.petshelter.core.model.AnimalType
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Radii
import com.petshelter.designsystem.Spacing
import com.petshelter.feature.animals.AnimalCard
import org.jetbrains.compose.resources.stringResource
import petshelter.composeapp.generated.resources.Res
import petshelter.composeapp.generated.resources.results_count
import petshelter.composeapp.generated.resources.results_empty
import petshelter.composeapp.generated.resources.results_match_label
import petshelter.composeapp.generated.resources.results_retake
import petshelter.composeapp.generated.resources.results_title

private val GRID_MIN_COLUMN_WIDTH = 160.dp

@Composable
fun QuestionnaireResults(
    matchedAnimals: List<MatchedAnimal>,
    onAnimalClick: (String) -> Unit,
    onRetake: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Spacer(Modifier.height(Spacing.Medium))

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = Spacing.Medium),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(Res.string.results_title),
                style = PetShelterTypography.Heading2,
                color = MaterialTheme.colorScheme.onBackground,
            )
            AppButton(
                text = stringResource(Res.string.results_retake),
                onClick = onRetake,
                variant = ButtonVariant.Secondary,
                size = ButtonSize.Small,
            )
        }

        Spacer(Modifier.height(Spacing.Small))

        Text(
            text = stringResource(Res.string.results_count, matchedAnimals.size),
            style = PetShelterTypography.Caption,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(horizontal = Spacing.Medium),
        )

        Spacer(Modifier.height(Spacing.Small))

        if (matchedAnimals.isEmpty()) {
            EmptyResults()
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = GRID_MIN_COLUMN_WIDTH),
                contentPadding = PaddingValues(Spacing.Medium),
                horizontalArrangement = Arrangement.spacedBy(Spacing.Medium),
                verticalArrangement = Arrangement.spacedBy(Spacing.Medium),
                modifier = Modifier.fillMaxSize(),
            ) {
                items(
                    items = matchedAnimals,
                    key = { it.animal.id },
                ) { matched ->
                    Box {
                        AnimalCard(
                            animal = matched.animal,
                            onClick = { onAnimalClick(matched.animal.id) },
                            modifier = Modifier.fillMaxWidth(),
                        )
                        MatchBadge(
                            percentage = matched.matchPercentage,
                            modifier =
                                Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(Spacing.Small),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MatchBadge(
    percentage: Int,
    modifier: Modifier = Modifier,
) {
    val badgeColor =
        when {
            percentage >= 80 -> MaterialTheme.colorScheme.tertiary
            percentage >= 60 -> MaterialTheme.colorScheme.primary
            percentage >= 40 -> PetShelterTheme.colors.Warning
            else -> MaterialTheme.colorScheme.outline
        }

    Box(
        modifier =
            modifier
                .background(
                    color = badgeColor,
                    shape = RoundedCornerShape(Radii.Full),
                ).padding(horizontal = Spacing.Small, vertical = Spacing.XXSmall),
    ) {
        Text(
            text = stringResource(Res.string.results_match_label, percentage),
            style = PetShelterTypography.Caption,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

@Composable
private fun EmptyResults() {
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
                text = stringResource(Res.string.results_empty),
                style = PetShelterTypography.Body,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
private fun QuestionnaireResultsPreview() {
    PetShelterTheme {
        QuestionnaireResults(
            matchedAnimals =
                listOf(
                    MatchedAnimal(previewAnimal(), 92),
                    MatchedAnimal(previewAnimal().copy(id = "2", name = "Max"), 75),
                    MatchedAnimal(previewAnimal().copy(id = "3", name = "Luna"), 45),
                ),
            onAnimalClick = {},
            onRetake = {},
        )
    }
}

@Preview
@Composable
private fun QuestionnaireResultsEmptyPreview() {
    PetShelterTheme {
        QuestionnaireResults(
            matchedAnimals = emptyList(),
            onAnimalClick = {},
            onRetake = {},
        )
    }
}

private fun previewAnimal() =
    Animal(
        id = "preview-1",
        animalType = AnimalType.DOG,
        name = "Pandora",
        sex = "Hembra",
        breed = "Malinois",
        size = AnimalSize.LARGE,
        ageMonths = 48,
        description = "Pandora is a beautiful Malinois Shepherd.",
        images = emptyList(),
        videos = emptyList(),
        sourceUrl = "https://example.com",
        scores =
            AnimalScores(
                friendly = 7,
                goodWithAnimals = 4,
                leashTrained = 6,
                reactive = 6,
                specialNeeds = 3,
                energy = 9,
                goodWithHumans = 6,
                shy = 2,
                activity = 9,
                trainability = 10,
                dailyActivityRequirement = 9,
            ),
    )
