package com.petshelter.feature.animals

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.petshelter.components.AppButton
import com.petshelter.components.ButtonSize
import com.petshelter.components.ButtonVariant
import com.petshelter.core.model.Animal
import com.petshelter.core.model.AnimalScores
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Radii
import com.petshelter.designsystem.Spacing
import com.petshelter.designsystem.icons.PetShelterIcons
import com.petshelter.util.openEmail
import com.petshelter.util.openUrl
import com.petshelter.util.transformImageUrl
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import petshelter.composeapp.generated.resources.Res
import petshelter.composeapp.generated.resources.back
import petshelter.composeapp.generated.resources.close
import petshelter.composeapp.generated.resources.detail_adopt_button
import petshelter.composeapp.generated.resources.detail_adopt_title
import petshelter.composeapp.generated.resources.detail_description
import petshelter.composeapp.generated.resources.detail_scores
import petshelter.composeapp.generated.resources.detail_scores_disclaimer
import petshelter.composeapp.generated.resources.detail_source
import petshelter.composeapp.generated.resources.detail_videos
import petshelter.composeapp.generated.resources.detail_watch_video
import petshelter.composeapp.generated.resources.score_activity
import petshelter.composeapp.generated.resources.score_daily_activity
import petshelter.composeapp.generated.resources.score_energy
import petshelter.composeapp.generated.resources.score_friendly
import petshelter.composeapp.generated.resources.score_good_with_animals
import petshelter.composeapp.generated.resources.score_good_with_humans
import petshelter.composeapp.generated.resources.score_leash_trained
import petshelter.composeapp.generated.resources.score_reactive
import petshelter.composeapp.generated.resources.score_shy
import petshelter.composeapp.generated.resources.score_special_needs
import petshelter.composeapp.generated.resources.score_trainability

private const val SHELTER_EMAIL = "contacto@protectoramalaga.com"

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
    var fullscreenImageIndex by remember { mutableStateOf<Int?>(null) }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isLargeScreen = maxWidth > 600.dp

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(
                            if (isLargeScreen) {
                                PetShelterTheme.colors.BackgroundSecondary
                            } else {
                                PetShelterTheme.colors.BackgroundPrimary
                            },
                        ).verticalScroll(rememberScrollState()),
            ) {
                ImageCarouselWithOverlay(
                    animal = animal,
                    onBack = onBack,
                    onImageClick = { index -> fullscreenImageIndex = index },
                    showInfoOverlay = !isLargeScreen,
                )
                if (isLargeScreen) {
                    Column(
                        modifier =
                            Modifier
                                .padding(horizontal = Spacing.Large, vertical = Spacing.Medium)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(Radii.Large))
                                .background(PetShelterTheme.colors.BackgroundPrimary),
                    ) {
                        AnimalInfoSection(animal = animal)
                        if (cleanDescription.isNotBlank()) {
                            DescriptionSection(description = cleanDescription)
                        }
                    }
                } else if (cleanDescription.isNotBlank()) {
                    DescriptionSection(description = cleanDescription)
                }
                ScoresSection(scores = animal.scores)
                if (videoLinks.isNotEmpty()) {
                    VideoCarousel(videoLinks = videoLinks)
                }
                SourceSection(sourceUrl = animal.sourceUrl)
                AdoptSection(animalName = animal.name)
                Spacer(Modifier.height(Spacing.XXLarge))
            }

            fullscreenImageIndex?.let { startIndex ->
                FullscreenImageViewer(
                    images = animal.images,
                    startIndex = startIndex,
                    onDismiss = { fullscreenImageIndex = null },
                )
            }
        }
    }
}

@Composable
private fun FullscreenImageViewer(
    images: List<String>,
    startIndex: Int,
    onDismiss: () -> Unit,
) {
    val pagerState = rememberPagerState(initialPage = startIndex, pageCount = { images.size })

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .background(Color.Black)
                .clickable(onClick = onDismiss),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            SubcomposeAsyncImage(
                model = transformImageUrl(images[page]),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit,
                loading = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp,
                        )
                    }
                },
                error = {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text("\uD83D\uDC3E", style = PetShelterTypography.Heading1)
                    }
                },
            )
        }

        // Close button
        Box(
            modifier =
                Modifier
                    .padding(Spacing.Large)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.6f))
                    .clickable(onClick = onDismiss)
                    .align(Alignment.TopEnd),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = PetShelterIcons.Close,
                contentDescription = stringResource(Res.string.close),
                modifier = Modifier.size(24.dp),
                tint = Color.White,
            )
        }

        if (images.size > 1) {
            PageIndicator(
                pageCount = images.size,
                currentPage = pagerState.currentPage,
                modifier =
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = Spacing.XLarge),
            )
        }
    }
}

@Composable
private fun ImageCarouselWithOverlay(
    animal: Animal,
    onBack: () -> Unit,
    onImageClick: (Int) -> Unit = {},
    showInfoOverlay: Boolean = true,
) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val isLargeScreen = maxWidth > 600.dp
        val carouselModifier =
            if (isLargeScreen) {
                Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp)
                    .aspectRatio(16f / 9f)
            } else {
                Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
            }

        if (animal.images.isNotEmpty()) {
            val pagerState = rememberPagerState(pageCount = { animal.images.size })

            Box(modifier = carouselModifier.background(PetShelterTheme.colors.BackgroundSecondary)) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                ) { page ->
                    SubcomposeAsyncImage(
                        model = transformImageUrl(animal.images[page]),
                        contentDescription = "${animal.name} - ${page + 1}",
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .clickable { onImageClick(page) },
                        contentScale = ContentScale.Crop,
                        loading = {
                            Box(
                                modifier = Modifier.fillMaxSize(),
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
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center,
                            ) {
                                Text("\uD83D\uDC3E", style = PetShelterTypography.Heading1)
                            }
                        },
                    )
                }

                Column(modifier = Modifier.align(Alignment.BottomStart).fillMaxWidth()) {
                    if (showInfoOverlay) {
                        AnimalInfoOverlay(animal = animal, modifier = Modifier.fillMaxWidth())
                    }
                    if (animal.images.size > 1) {
                        Box(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .background(Color.Black.copy(alpha = 0.5f))
                                    .padding(vertical = Spacing.Small),
                            contentAlignment = Alignment.Center,
                        ) {
                            PageIndicator(
                                pageCount = animal.images.size,
                                currentPage = pagerState.currentPage,
                            )
                        }
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
        } else {
            Box(
                modifier =
                    carouselModifier
                        .background(PetShelterTheme.colors.BackgroundSecondary),
                contentAlignment = Alignment.Center,
            ) {
                Text("\uD83D\uDC3E", style = PetShelterTypography.Heading1)
            }
        }
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
private fun AnimalInfoOverlay(
    animal: Animal,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier.background(
                Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                ),
            ),
    ) {
        Column(modifier = Modifier.padding(horizontal = Spacing.Large, vertical = Spacing.Medium)) {
            Text(
                text = animal.name,
                style = PetShelterTypography.Heading2,
                color = Color.White,
            )
            Spacer(Modifier.height(Spacing.XSmall))
            Text(
                text = animal.breed,
                style = PetShelterTypography.Body,
                color = Color.White.copy(alpha = 0.8f),
            )
            Spacer(Modifier.height(Spacing.Small))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
                verticalArrangement = Arrangement.spacedBy(Spacing.Small),
            ) {
                OverlayBadge(text = sexSymbol(animal.sex) + " " + sexDisplayLabel(animal.sex))
                OverlayBadge(text = sizeLabel(animal.size))
                animal.ageMonths?.let { months ->
                    OverlayBadge(text = formatAge(months))
                }
            }
        }
    }
}

@Composable
private fun OverlayBadge(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier =
            modifier
                .background(
                    color = Color.White.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(Radii.Full),
                ).padding(horizontal = Spacing.Small, vertical = Spacing.XXSmall),
    ) {
        Text(
            text = text,
            style = PetShelterTypography.Caption,
            color = Color.White,
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AnimalInfoSection(animal: Animal) {
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
        Spacer(Modifier.height(Spacing.Small))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
            verticalArrangement = Arrangement.spacedBy(Spacing.Small),
        ) {
            InfoBadge(text = sexSymbol(animal.sex) + " " + sexDisplayLabel(animal.sex))
            InfoBadge(text = sizeLabel(animal.size))
            animal.ageMonths?.let { months ->
                InfoBadge(text = formatAge(months))
            }
        }
    }
}

@Composable
private fun InfoBadge(
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
        ScoreRow(label = stringResource(Res.string.score_friendly), value = scores.friendly)
        ScoreRow(label = stringResource(Res.string.score_good_with_animals), value = scores.goodWithAnimals)
        ScoreRow(label = stringResource(Res.string.score_good_with_humans), value = scores.goodWithHumans)
        ScoreRow(label = stringResource(Res.string.score_leash_trained), value = scores.leashTrained)
        ScoreRow(label = stringResource(Res.string.score_energy), value = scores.energy)
        ScoreRow(label = stringResource(Res.string.score_activity), value = scores.activity)
        ScoreRow(label = stringResource(Res.string.score_trainability), value = scores.trainability)
        ScoreRow(label = stringResource(Res.string.score_reactive), value = scores.reactive)
        ScoreRow(label = stringResource(Res.string.score_shy), value = scores.shy)
        ScoreRow(label = stringResource(Res.string.score_special_needs), value = scores.specialNeeds)
        ScoreRow(label = stringResource(Res.string.score_daily_activity), value = scores.dailyActivityRequirement)
        Spacer(Modifier.height(Spacing.Medium))
        Text(
            text = stringResource(Res.string.detail_scores_disclaimer),
            style = PetShelterTypography.Caption,
            color = PetShelterTheme.colors.TextTertiary,
        )
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
        value >= 5 -> PetShelterTheme.colors.Primary
        else -> PetShelterTheme.colors.AnnotationOrange
    }

@Composable
private fun VideoCarousel(videoLinks: List<String>) {
    SectionDivider()
    Column(modifier = Modifier.padding(vertical = Spacing.Medium)) {
        Text(
            text = stringResource(Res.string.detail_videos),
            style = PetShelterTypography.Heading3,
            color = PetShelterTheme.colors.TextPrimary,
            modifier = Modifier.padding(horizontal = Spacing.Large),
        )
        Spacer(Modifier.height(Spacing.Small))

        val pagerState = rememberPagerState(pageCount = { videoLinks.size })

        HorizontalPager(
            state = pagerState,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Spacing.Medium),
            pageSpacing = Spacing.Medium,
        ) { page ->
            VideoThumbnail(url = videoLinks[page])
        }

        if (videoLinks.size > 1) {
            Spacer(Modifier.height(Spacing.Small))
            PageIndicator(
                pageCount = videoLinks.size,
                currentPage = pagerState.currentPage,
                modifier =
                    Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = Spacing.XSmall),
            )
        }
    }
}

@Composable
private fun VideoThumbnail(url: String) {
    val videoId = extractYouTubeVideoId(url)
    val thumbnailUrl = videoId?.let { "https://img.youtube.com/vi/$it/hqdefault.jpg" }

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(Radii.Medium))
                .clickable { openUrl(url) },
    ) {
        if (thumbnailUrl != null) {
            SubcomposeAsyncImage(
                model = transformImageUrl(thumbnailUrl),
                contentDescription = stringResource(Res.string.detail_watch_video),
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .background(PetShelterTheme.colors.BackgroundSecondary),
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
                    VideoFallbackRow(url = url)
                },
            )
            PlayButtonOverlay()
        } else {
            VideoFallbackRow(url = url)
        }
    }
}

@Composable
private fun PlayButtonOverlay() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier =
                Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(PetShelterTheme.colors.BackgroundPrimary.copy(alpha = 0.85f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = PetShelterIcons.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = PetShelterTheme.colors.Primary,
            )
        }
    }
}

@Composable
private fun VideoFallbackRow(url: String) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .background(PetShelterTheme.colors.BackgroundSecondary)
                .padding(Spacing.Medium),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = PetShelterIcons.PlayArrow,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = PetShelterTheme.colors.Primary,
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

internal fun extractYouTubeVideoId(url: String): String? {
    val patterns =
        listOf(
            Regex("youtube\\.com/shorts/([\\w-]+)"),
            Regex("youtube\\.com/embed//?([\\w-]+)"),
            Regex("youtube\\.com/watch\\?v=([\\w-]+)"),
            Regex("youtu\\.be/([\\w-]+)"),
        )
    for (pattern in patterns) {
        pattern
            .find(url)
            ?.groupValues
            ?.get(1)
            ?.let { return it }
    }
    return null
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
            modifier = Modifier.clickable { openUrl(sourceUrl) },
        )
    }
}

@Composable
private fun AdoptSection(animalName: String) {
    SectionDivider()
    Column(modifier = Modifier.padding(horizontal = Spacing.Large, vertical = Spacing.Medium)) {
        SectionTitle(text = stringResource(Res.string.detail_adopt_title))
        Spacer(Modifier.height(Spacing.Small))
        AppButton(
            text = stringResource(Res.string.detail_adopt_button, animalName),
            onClick = { openEmail(SHELTER_EMAIL) },
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            modifier = Modifier.fillMaxWidth(),
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
