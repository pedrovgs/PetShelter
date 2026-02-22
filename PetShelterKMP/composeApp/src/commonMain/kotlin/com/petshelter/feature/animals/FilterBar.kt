package com.petshelter.feature.animals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.petshelter.core.model.AnimalSex
import com.petshelter.core.model.AnimalSize
import com.petshelter.core.model.AnimalType
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Radii
import com.petshelter.designsystem.Spacing
import com.petshelter.designsystem.icons.PetShelterIcons
import org.jetbrains.compose.resources.stringResource
import petshelter.composeapp.generated.resources.Res
import petshelter.composeapp.generated.resources.filter_all_ages
import petshelter.composeapp.generated.resources.filter_all_animals
import petshelter.composeapp.generated.resources.filter_all_breeds
import petshelter.composeapp.generated.resources.filter_all_sexes
import petshelter.composeapp.generated.resources.filter_all_sizes
import petshelter.composeapp.generated.resources.filter_cats_only
import petshelter.composeapp.generated.resources.filter_dogs_only
import petshelter.composeapp.generated.resources.filter_sex_female
import petshelter.composeapp.generated.resources.filter_sex_male
import petshelter.composeapp.generated.resources.filter_size_extra_large
import petshelter.composeapp.generated.resources.filter_size_large
import petshelter.composeapp.generated.resources.filter_size_medium
import petshelter.composeapp.generated.resources.filter_size_small

@Composable
fun FilterBar(
    selectedSex: AnimalSex?,
    selectedSize: AnimalSize?,
    selectedBreed: String?,
    selectedAge: AgeFilter?,
    availableBreeds: List<String>,
    onSexChanged: (AnimalSex?) -> Unit,
    onSizeChanged: (AnimalSize?) -> Unit,
    onBreedChanged: (String?) -> Unit,
    onAgeChanged: (AgeFilter?) -> Unit,
    modifier: Modifier = Modifier,
    showAnimalTypeFilter: Boolean = false,
    selectedAnimalType: AnimalType? = null,
    onAnimalTypeChanged: (AnimalType?) -> Unit = {},
) {
    LazyRow(
        modifier = modifier.padding(horizontal = Spacing.Medium),
        horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
    ) {
        if (showAnimalTypeFilter) {
            item {
                AnimalTypeFilterDropdown(
                    selectedAnimalType = selectedAnimalType,
                    onAnimalTypeChanged = onAnimalTypeChanged,
                )
            }
        }
        item {
            SexFilterDropdown(
                selectedSex = selectedSex,
                onSexChanged = onSexChanged,
            )
        }
        item {
            SizeFilterDropdown(
                selectedSize = selectedSize,
                onSizeChanged = onSizeChanged,
            )
        }
        item {
            AgeFilterDropdown(
                selectedAge = selectedAge,
                onAgeChanged = onAgeChanged,
            )
        }
        item {
            BreedFilterDropdown(
                selectedBreed = selectedBreed,
                availableBreeds = availableBreeds,
                onBreedChanged = onBreedChanged,
            )
        }
    }
}

@Composable
private fun AnimalTypeFilterDropdown(
    selectedAnimalType: AnimalType?,
    onAnimalTypeChanged: (AnimalType?) -> Unit,
) {
    val allLabel = stringResource(Res.string.filter_all_animals)
    val dogsLabel = stringResource(Res.string.filter_dogs_only)
    val catsLabel = stringResource(Res.string.filter_cats_only)
    val displayText =
        when (selectedAnimalType) {
            AnimalType.DOG -> dogsLabel
            AnimalType.CAT -> catsLabel
            null -> allLabel
        }
    val options = listOf(null, AnimalType.DOG, AnimalType.CAT)
    val labels = listOf(allLabel, "\uD83D\uDC36 $dogsLabel", "\uD83D\uDC31 $catsLabel")

    FilterChipDropdown(
        label = displayText,
        isActive = selectedAnimalType != null,
        options = options,
        optionLabels = labels,
        onOptionSelected = onAnimalTypeChanged,
    )
}

@Composable
private fun SexFilterDropdown(
    selectedSex: AnimalSex?,
    onSexChanged: (AnimalSex?) -> Unit,
) {
    val allLabel = stringResource(Res.string.filter_all_sexes)
    val femaleLabel = stringResource(Res.string.filter_sex_female)
    val maleLabel = stringResource(Res.string.filter_sex_male)
    val displayText =
        when (selectedSex) {
            AnimalSex.FEMALE -> femaleLabel
            AnimalSex.MALE -> maleLabel
            null -> allLabel
        }
    val options = listOf(null, AnimalSex.FEMALE, AnimalSex.MALE)
    val labels = listOf(allLabel, "\u2640\uFE0F $femaleLabel", "\u2642\uFE0F $maleLabel")

    FilterChipDropdown(
        label = displayText,
        isActive = selectedSex != null,
        options = options,
        optionLabels = labels,
        onOptionSelected = onSexChanged,
    )
}

@Composable
private fun SizeFilterDropdown(
    selectedSize: AnimalSize?,
    onSizeChanged: (AnimalSize?) -> Unit,
) {
    val allLabel = stringResource(Res.string.filter_all_sizes)
    val displayText = selectedSize?.let { sizeDisplayLabel(it) } ?: allLabel
    val options = listOf(null) + AnimalSize.entries
    val labels =
        listOf(allLabel) +
            AnimalSize.entries.map { sizeDisplayLabel(it) }

    FilterChipDropdown(
        label = displayText,
        isActive = selectedSize != null,
        options = options,
        optionLabels = labels,
        onOptionSelected = { onSizeChanged(it) },
    )
}

@Composable
private fun AgeFilterDropdown(
    selectedAge: AgeFilter?,
    onAgeChanged: (AgeFilter?) -> Unit,
) {
    val allLabel = stringResource(Res.string.filter_all_ages)
    val displayText = selectedAge?.label ?: allLabel
    val options = listOf(null) + AgeFilter.entries
    val labels = listOf(allLabel) + AgeFilter.entries.map { it.label }

    FilterChipDropdown(
        label = displayText,
        isActive = selectedAge != null,
        options = options,
        optionLabels = labels,
        onOptionSelected = { onAgeChanged(it) },
    )
}

@Composable
private fun BreedFilterDropdown(
    selectedBreed: String?,
    availableBreeds: List<String>,
    onBreedChanged: (String?) -> Unit,
) {
    val allLabel = stringResource(Res.string.filter_all_breeds)
    val displayText = selectedBreed ?: allLabel
    val options = listOf<String?>(null) + availableBreeds
    val labels = listOf(allLabel) + availableBreeds

    FilterChipDropdown(
        label = displayText,
        isActive = selectedBreed != null,
        options = options,
        optionLabels = labels,
        onOptionSelected = onBreedChanged,
    )
}

@Composable
private fun <T> FilterChipDropdown(
    label: String,
    isActive: Boolean,
    options: List<T>,
    optionLabels: List<String>,
    onOptionSelected: (T) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        FilterChip(
            label = label,
            isActive = isActive,
            onClick = { expanded = true },
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEachIndexed { index, option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = optionLabels[index],
                            style = PetShelterTypography.BodySmall,
                        )
                    },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Composable
private fun FilterChip(
    label: String,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        if (isActive) {
            PetShelterTheme.colors.Primary
        } else {
            PetShelterTheme.colors.BackgroundSecondary
        }
    val textColor =
        if (isActive) {
            PetShelterTheme.colors.TextInverse
        } else {
            PetShelterTheme.colors.TextPrimary
        }

    Row(
        modifier =
            modifier
                .clip(RoundedCornerShape(Radii.Full))
                .background(backgroundColor)
                .clickable(onClick = onClick)
                .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = PetShelterTypography.Caption,
            color = textColor,
        )
        Spacer(Modifier.width(Spacing.XSmall))
        Icon(
            imageVector = PetShelterIcons.ChevronDown,
            contentDescription = null,
            modifier = Modifier.size(12.dp),
            tint = textColor,
        )
    }
}

@Composable
private fun sizeDisplayLabel(size: AnimalSize): String =
    when (size) {
        AnimalSize.SMALL -> stringResource(Res.string.filter_size_small)
        AnimalSize.MEDIUM -> stringResource(Res.string.filter_size_medium)
        AnimalSize.LARGE -> stringResource(Res.string.filter_size_large)
        AnimalSize.EXTRA_LARGE -> stringResource(Res.string.filter_size_extra_large)
    }

@Preview
@Composable
private fun FilterBarPreview() {
    PetShelterTheme {
        FilterBar(
            selectedSex = null,
            selectedSize = null,
            selectedBreed = null,
            selectedAge = null,
            availableBreeds = listOf("Malinois", "Bodeguero", "Mestizo"),
            onSexChanged = {},
            onSizeChanged = {},
            onBreedChanged = {},
            onAgeChanged = {},
        )
    }
}

@Preview
@Composable
private fun FilterBarActivePreview() {
    PetShelterTheme {
        FilterBar(
            selectedSex = AnimalSex.FEMALE,
            selectedSize = AnimalSize.LARGE,
            selectedBreed = null,
            selectedAge = AgeFilter.UP_TO_4_YEARS,
            availableBreeds = listOf("Malinois", "Bodeguero", "Mestizo"),
            onSexChanged = {},
            onSizeChanged = {},
            onBreedChanged = {},
            onAgeChanged = {},
        )
    }
}
