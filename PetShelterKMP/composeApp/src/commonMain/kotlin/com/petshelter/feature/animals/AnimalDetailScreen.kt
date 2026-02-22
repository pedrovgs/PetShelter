package com.petshelter.feature.animals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.petshelter.core.model.Animal
import com.petshelter.core.model.AnimalScores
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Radii
import com.petshelter.designsystem.Spacing
import com.petshelter.designsystem.icons.PetShelterIcons
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import petshelter.composeapp.generated.resources.Res
import petshelter.composeapp.generated.resources.back
import petshelter.composeapp.generated.resources.detail_description
import petshelter.composeapp.generated.resources.detail_scores
import petshelter.composeapp.generated.resources.detail_source
import petshelter.composeapp.generated.resources.detail_videos

@Composable
fun AnimalDetailScreen(
    viewModel: AnimalDetailViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    AnimalDetailContent(
        state = uiState,
        onBack = onBack,
        modifier = modifier,
    )
}

@Composable
internal fun AnimalDetailContent(
    state: AnimalDetailUiState,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        state.isLoading -> LoadingDetail(modifier)
        state.errorMessage != null -> ErrorDetail(message = state.errorMessage, onBack = onBack, modifier = modifier)
        state.animal != null ->
            AnimalDetailBody(
                animal = state.animal,
                cleanDescription = state.cleanDescription,
                videoLinks = state.videoLinks,
                onBack = onBack,
                modifier = modifier,
            )
    }
}

@Composable
private fun LoadingDetail(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = PetShelterTheme.colors.Primary)
    }
}

@Composable
private fun ErrorDetail(
    message: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize().padding(Spacing.Large),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = message,
            style = PetShelterTypography.Body,
            color = PetShelterTheme.colors.Error,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(Spacing.Medium))
        BackButton(onBack = onBack)
    }
}

@Composable
private fun AnimalDetailBody(
    animal: Animal,
    cleanDescription: String,
    videoLinks: List<String>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
    ) {
        ImageCarousel(images = animal.images, name = animal.name, onBack = onBack)
        AnimalInfoHeader(animal = animal)
        if (cleanDescription.isNotBlank()) {
            DescriptionSection(description = cleanDescription)
        }
        ScoresSection(scores = animal.scores)
        if (videoLinks.isNotEmpty()) {
            VideosSection(videoLinks = videoLinks)
        }
        SourceSection(sourceUrl = animal.sourceUrl)
        Spacer(Modifier.height(Spacing.XXLarge))
    }
}

@Composable
private fun ImageCarousel(
    images: List<String>,
    name: String,
    onBack: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        if (images.isNotEmpty()) {
            val pagerState = rememberPagerState(pageCount = { images.size })

            Column {
                HorizontalPager(
                    state = pagerState,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .aspectRatio(4f / 3f),
                ) { page ->
                    SubcomposeAsyncImage(
                        model = images[page],
                        contentDescription = "$name - ${page + 1}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        loading = {
                            Box(
                                modifier = Modifier.fillMaxSize().background(PetShelterTheme.colors.BackgroundSecondary),
                                contentAlignment = Alignment.Center,
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = PetShelterTheme.colors.Primary,
                                    strokeWidth = 1.5.dp,
                                )
                            }
                        },
                        error = {
                            Box(
                                modifier = Modifier.fillMaxSize().background(PetShelterTheme.colors.BackgroundSecondary),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text("\uD83D\uDC3E", style = PetShelterTypography.Heading1)
                            }
                        },
                    )
                }

                if (images.size > 1) {
                    PageIndicator(
                        pageCount = images.size,
                        currentPage = pagerState.currentPage,
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(vertical = Spacing.Small),
                    )
                }
            }
        } else {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 3f)
                        .background(PetShelterTheme.colors.BackgroundSecondary),
                contentAlignment = Alignment.Center,
            ) {
                Text("\uD83D\uDC3E", style = PetShelterTypography.Heading1)
            }
        }

        BackButton(
            onBack = onBack,
            modifier =
                Modifier
                    .padding(Spacing.Medium)
                    .align(Alignment.TopStart),
        )
    }
}

@Composable
private fun BackButton(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(PetShelterTheme.colors.BackgroundPrimary.copy(alpha = 0.85f))
                .clickable(onClick = onBack),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = PetShelterIcons.ArrowBack,
            contentDescription = stringResource(Res.string.back),
            modifier = Modifier.size(20.dp),
            tint = PetShelterTheme.colors.TextPrimary,
        )
    }
}

@Composable
private fun PageIndicator(
    pageCount: Int,
    currentPage: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Spacing.XSmall),
    ) {
        repeat(pageCount) { index ->
            val color =
                if (index == currentPage) {
                    PetShelterTheme.colors.Primary
                } else {
                    PetShelterTheme.colors.BorderLight
                }
            Box(
                modifier =
                    Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color),
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AnimalInfoHeader(animal: Animal) {
    Column(modifier = Modifier.padding(horizontal = Spacing.Large, vertical = Spacing.Medium)) {
        Text(
            text = animal.name,
            style = PetShelterTypography.Heading2,
            color = PetShelterTheme.colors.TextPrimary,
        )
        Spacer(Modifier.height(Spacing.XSmall))
        Text(
            text = animal.breed,
            style = PetShelterTypography.Body,
            color = PetShelterTheme.colors.TextSecondary,
        )
        Spacer(Modifier.height(Spacing.Medium))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            verticalArrangement = Arrangement.spacedBy(Spacing.Small),
        ) {
            AnimalBadge(text = sexEmoji(animal.sex) + " " + animal.sex)
            AnimalBadge(text = sizeLabel(animal.size))
            animal.ageMonths?.let { months ->
                AnimalBadge(text = formatAge(months))
            }
        }
    }
}

private fun sexEmoji(sex: String): String =
    when (sex) {
        "Hembra" -> "\u2640\uFE0F"
        "Macho" -> "\u2642\uFE0F"
        else -> ""
    }

@Composable
private fun DescriptionSection(description: String) {
    SectionDivider()
    Column(modifier = Modifier.padding(horizontal = Spacing.Large, vertical = Spacing.Medium)) {
        SectionTitle(text = stringResource(Res.string.detail_description))
        Spacer(Modifier.height(Spacing.Small))
        Text(
            text = description,
            style = PetShelterTypography.Body,
            color = PetShelterTheme.colors.TextPrimary,
        )
    }
}

@Composable
private fun ScoresSection(scores: AnimalScores) {
    SectionDivider()
    Column(modifier = Modifier.padding(horizontal = Spacing.Large, vertical = Spacing.Medium)) {
        SectionTitle(text = stringResource(Res.string.detail_scores))
        Spacer(Modifier.height(Spacing.Medium))
        ScoreRow(label = "\uD83E\uDD1D Friendly", value = scores.friendly)
        ScoreRow(label = "\uD83D\uDC3E Good with animals", value = scores.goodWithAnimals)
        ScoreRow(label = "\uD83D\uDC65 Good with humans", value = scores.goodWithHumans)
        ScoreRow(label = "\uD83E\uDDAE Leash trained", value = scores.leashTrained)
        ScoreRow(label = "\u26A1 Energy", value = scores.energy)
        ScoreRow(label = "\uD83C\uDFC3 Activity", value = scores.activity)
        ScoreRow(label = "\uD83C\uDF93 Trainability", value = scores.trainability)
        ScoreRow(label = "\uD83D\uDE28 Reactive", value = scores.reactive)
        ScoreRow(label = "\uD83D\uDE33 Shy", value = scores.shy)
        ScoreRow(label = "\uD83C\uDFE5 Special needs", value = scores.specialNeeds)
        ScoreRow(label = "\uD83D\uDEB6 Daily activity", value = scores.dailyActivityRequirement)
    }
}

@Composable
private fun ScoreRow(
    label: String,
    value: Int,
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = Spacing.XSmall),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = label,
            style = PetShelterTypography.Caption,
            color = PetShelterTheme.colors.TextSecondary,
            modifier = Modifier.width(160.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        LinearProgressIndicator(
            progress = { value / 10f },
            modifier =
                Modifier
                    .weight(1f)
                    .height(8.dp)
                    .clip(RoundedCornerShape(Radii.Full)),
            color = scoreColor(value),
            trackColor = PetShelterTheme.colors.BackgroundTertiary,
        )
        Spacer(Modifier.width(Spacing.Small))
        Text(
            text = "$value",
            style = PetShelterTypography.Caption,
            color = PetShelterTheme.colors.TextSecondary,
            modifier = Modifier.width(20.dp),
            textAlign = TextAlign.End,
        )
    }
}

@Composable
private fun scoreColor(value: Int) =
    when {
        value >= 8 -> PetShelterTheme.colors.Success
        value >= 5 -> PetShelterTheme.colors.Warning
        else -> PetShelterTheme.colors.Error
    }

@Composable
private fun VideosSection(videoLinks: List<String>) {
    SectionDivider()
    Column(modifier = Modifier.padding(horizontal = Spacing.Large, vertical = Spacing.Medium)) {
        SectionTitle(text = stringResource(Res.string.detail_videos))
        Spacer(Modifier.height(Spacing.Small))
        videoLinks.forEach { url ->
            VideoLinkRow(url = url)
            Spacer(Modifier.height(Spacing.XSmall))
        }
    }
}

@Composable
private fun VideoLinkRow(url: String) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Radii.Medium))
                .background(PetShelterTheme.colors.BackgroundSecondary)
                .padding(Spacing.Medium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "\u25B6\uFE0F",
            style = PetShelterTypography.Body,
        )
        Spacer(Modifier.width(Spacing.Small))
        Text(
            text = url,
            style = PetShelterTypography.Caption,
            color = PetShelterTheme.colors.Primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun SourceSection(sourceUrl: String) {
    SectionDivider()
    Column(modifier = Modifier.padding(horizontal = Spacing.Large, vertical = Spacing.Medium)) {
        SectionTitle(text = stringResource(Res.string.detail_source))
        Spacer(Modifier.height(Spacing.Small))
        Text(
            text = sourceUrl,
            style = PetShelterTypography.BodySmall,
            color = PetShelterTheme.colors.Primary,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = PetShelterTypography.Heading3,
        color = PetShelterTheme.colors.TextPrimary,
    )
}

@Composable
private fun SectionDivider() {
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = Spacing.Large),
        color = PetShelterTheme.colors.BorderLight,
    )
}

@Composable
fun AnimalDetailRoute(
    animalId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel =
        koinViewModel<AnimalDetailViewModel>(
            key = animalId,
            parameters = { parametersOf(animalId) },
        )
    AnimalDetailScreen(
        viewModel = viewModel,
        onBack = onBack,
        modifier = modifier,
    )
}

@Preview
@Composable
private fun AnimalDetailContentLoadingPreview() {
    PetShelterTheme {
        AnimalDetailContent(
            state = AnimalDetailUiState(isLoading = true),
            onBack = {},
        )
    }
}

@Preview
@Composable
private fun AnimalDetailContentPreview() {
    PetShelterTheme {
        AnimalDetailContent(
            state =
                AnimalDetailUiState(
                    isLoading = false,
                    animal = previewAnimal(),
                    cleanDescription = "Pandora is a beautiful Malinois Shepherd, about 4 years old.",
                    videoLinks = listOf("https://youtube.com/shorts/abc123"),
                ),
            onBack = {},
        )
    }
}
