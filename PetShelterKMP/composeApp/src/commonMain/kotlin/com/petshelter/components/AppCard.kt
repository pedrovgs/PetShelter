package com.petshelter.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import com.petshelter.designsystem.AnimationDuration
import com.petshelter.designsystem.Elevation
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Radii
import com.petshelter.designsystem.Spacing

@Composable
fun AppCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit,
) {
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val elevation by animateDpAsState(
        targetValue =
            when {
                isPressed -> Elevation.Small
                isHovered -> Elevation.Large
                else -> Elevation.Small
            },
        animationSpec = tween(AnimationDuration.FAST),
    )

    val scale by animateFloatAsState(
        targetValue =
            when {
                isPressed -> 0.98f
                isHovered -> 1.02f
                else -> 1f
            },
        animationSpec = tween(AnimationDuration.FAST),
    )

    Card(
        onClick = onClick,
        modifier = modifier.scale(scale),
        interactionSource = interactionSource,
        shape = RoundedCornerShape(Radii.Large),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation),
        colors =
            CardDefaults.cardColors(
                containerColor = PetShelterTheme.colors.BackgroundPrimary,
            ),
    ) {
        content()
    }
}

@Preview
@Composable
private fun AppCardPreview() {
    AppCard(onClick = {}) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(Spacing.Medium),
        ) {
            Text("Card Title", style = PetShelterTypography.Label)
            Text("Card content goes here", style = PetShelterTypography.BodySmall)
        }
    }
}
