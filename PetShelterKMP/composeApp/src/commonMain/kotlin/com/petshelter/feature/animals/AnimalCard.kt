package com.petshelter.feature.animals

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.petshelter.components.AppCard
import com.petshelter.core.model.Animal
import com.petshelter.core.model.AnimalScores
import com.petshelter.core.model.AnimalSize
import com.petshelter.core.model.AnimalType
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Radii
import com.petshelter.designsystem.Spacing

@Composable
fun AnimalCard(
    animal: Animal,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column {
            AnimalImage(imageUrl = animal.images.firstOrNull(), name = animal.name)
            AnimalCardContent(animal = animal)
        }
    }
}

@Composable
private fun AnimalImage(
    imageUrl: String?,
    name: String,
) {
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(topStart = Radii.Large, topEnd = Radii.Large))
                .background(PetShelterTheme.colors.BackgroundSecondary),
        contentAlignment = Alignment.Center,
    ) {
        if (imageUrl != null) {
            SubcomposeAsyncImage(
                model = imageUrl,
                contentDescription = name,
                modifier = Modifier.matchParentSize(),
                contentScale = ContentScale.Crop,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = PetShelterTheme.colors.Primary,
                        strokeWidth = 1.5.dp,
                    )
                },
                error = {
                    ImagePlaceholder()
                },
            )
        } else {
            ImagePlaceholder()
        }
    }
}

@Composable
private fun ImagePlaceholder() {
    Text(
        text = "\uD83D\uDC3E",
        style = PetShelterTypography.Heading1,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AnimalCardContent(animal: Animal) {
    Column(
        modifier = Modifier.padding(Spacing.Medium),
    ) {
        Text(
            text = animal.name,
            style = PetShelterTypography.Label,
            color = PetShelterTheme.colors.TextPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(Modifier.height(Spacing.XSmall))
        Text(
            text = animal.breed,
            style = PetShelterTypography.Caption,
            color = PetShelterTheme.colors.TextSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(Modifier.height(Spacing.Small))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Spacing.XSmall),
            verticalArrangement = Arrangement.spacedBy(Spacing.XSmall),
        ) {
            AnimalBadge(text = sexEmoji(animal.sex) + " " + animal.sex)
            AnimalBadge(text = sizeLabel(animal.size))
            animal.ageMonths?.let { months ->
                AnimalBadge(text = formatAge(months))
            }
        }
    }
}

@Composable
fun AnimalBadge(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .background(
                    color = PetShelterTheme.colors.BackgroundSecondary,
                    shape = RoundedCornerShape(Radii.Full),
                ).padding(horizontal = Spacing.Small, vertical = Spacing.XXSmall),
    ) {
        Text(
            text = text,
            style = PetShelterTypography.Caption,
            color = PetShelterTheme.colors.TextSecondary,
        )
    }
}

private fun sexEmoji(sex: String): String =
    when (sex) {
        "Hembra" -> "\u2640\uFE0F"
        "Macho" -> "\u2642\uFE0F"
        else -> ""
    }

internal fun sizeLabel(size: AnimalSize): String =
    when (size) {
        AnimalSize.SMALL -> "S"
        AnimalSize.MEDIUM -> "M"
        AnimalSize.LARGE -> "L"
        AnimalSize.EXTRA_LARGE -> "XL"
    }

internal fun formatAge(months: Int): String {
    val years = months / 12
    val remainingMonths = months % 12
    return when {
        years == 0 -> "${remainingMonths}m"
        remainingMonths == 0 -> "${years}y"
        else -> "${years}y ${remainingMonths}m"
    }
}

@Preview
@Composable
private fun AnimalCardPreview() {
    PetShelterTheme {
        AnimalCard(
            animal = previewAnimal(),
            onClick = {},
            modifier = Modifier.width(180.dp),
        )
    }
}

internal fun previewAnimal() =
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
