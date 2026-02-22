package com.petshelter.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.petshelter.designsystem.AnimationDuration
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Radii
import com.petshelter.designsystem.Spacing

enum class ButtonVariant { Primary, Secondary, Ghost, Danger }

enum class ButtonSize { Small, Medium, Large }

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Primary,
    size: ButtonSize = ButtonSize.Medium,
    enabled: Boolean = true,
    loading: Boolean = false,
    leadingIcon: (@Composable () -> Unit)? = null,
    shape: Shape = RoundedCornerShape(Radii.Medium),
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val isPressed by interactionSource.collectIsPressedAsState()

    val containerColor by animateColorAsState(
        targetValue =
            when (variant) {
                ButtonVariant.Primary ->
                    when {
                        isPressed -> PetShelterTheme.colors.PrimaryDark
                        isHovered -> PetShelterTheme.colors.PrimaryLight
                        else -> PetShelterTheme.colors.Primary
                    }
                ButtonVariant.Secondary ->
                    when {
                        isPressed -> PetShelterTheme.colors.BackgroundTertiary
                        isHovered -> PetShelterTheme.colors.BackgroundHover
                        else -> PetShelterTheme.colors.BackgroundSecondary
                    }
                ButtonVariant.Ghost ->
                    when {
                        isPressed -> PetShelterTheme.colors.BackgroundTertiary
                        isHovered -> PetShelterTheme.colors.BackgroundHover
                        else -> Color.Transparent
                    }
                ButtonVariant.Danger ->
                    when {
                        isPressed -> PetShelterTheme.colors.ErrorDark
                        isHovered -> PetShelterTheme.colors.ErrorHover
                        else -> PetShelterTheme.colors.Error
                    }
            },
        animationSpec = tween(AnimationDuration.FAST),
    )

    val contentColor =
        when (variant) {
            ButtonVariant.Primary, ButtonVariant.Danger -> PetShelterTheme.colors.TextInverse
            ButtonVariant.Secondary -> PetShelterTheme.colors.TextPrimary
            ButtonVariant.Ghost -> PetShelterTheme.colors.Primary
        }

    val height =
        when (size) {
            ButtonSize.Small -> 32.dp
            ButtonSize.Medium -> 40.dp
            ButtonSize.Large -> 48.dp
        }

    val padding =
        when (size) {
            ButtonSize.Small -> PaddingValues(horizontal = Spacing.Small, vertical = Spacing.XSmall)
            ButtonSize.Medium -> PaddingValues(horizontal = Spacing.Medium, vertical = Spacing.Small)
            ButtonSize.Large -> PaddingValues(horizontal = Spacing.Large, vertical = Spacing.Medium)
        }

    val textStyle =
        when (size) {
            ButtonSize.Small -> PetShelterTypography.Caption
            ButtonSize.Medium -> PetShelterTypography.Button
            ButtonSize.Large -> PetShelterTypography.Body
        }

    when (variant) {
        ButtonVariant.Ghost -> {
            TextButton(
                onClick = onClick,
                modifier = modifier.height(height),
                enabled = enabled && !loading,
                interactionSource = interactionSource,
                contentPadding = padding,
                colors =
                    ButtonDefaults.textButtonColors(
                        containerColor = containerColor,
                        contentColor = contentColor,
                    ),
            ) {
                ButtonContent(text, textStyle, loading, contentColor, leadingIcon)
            }
        }
        ButtonVariant.Secondary -> {
            OutlinedButton(
                onClick = onClick,
                modifier = modifier.height(height),
                enabled = enabled && !loading,
                interactionSource = interactionSource,
                contentPadding = padding,
                shape = shape,
                colors =
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = containerColor,
                        contentColor = contentColor,
                    ),
            ) {
                ButtonContent(text, textStyle, loading, contentColor, leadingIcon)
            }
        }
        else -> {
            Button(
                onClick = onClick,
                modifier = modifier.height(height),
                enabled = enabled && !loading,
                interactionSource = interactionSource,
                contentPadding = padding,
                shape = shape,
                colors =
                    ButtonDefaults.buttonColors(
                        containerColor = containerColor,
                        contentColor = contentColor,
                    ),
            ) {
                ButtonContent(text, textStyle, loading, contentColor, leadingIcon)
            }
        }
    }
}

@Composable
private fun ButtonContent(
    text: String,
    textStyle: androidx.compose.ui.text.TextStyle,
    loading: Boolean,
    contentColor: Color,
    leadingIcon: (@Composable () -> Unit)?,
) {
    if (loading) {
        CircularProgressIndicator(
            modifier = Modifier.size(16.dp),
            color = contentColor,
            strokeWidth = 2.dp,
        )
        Spacer(Modifier.width(Spacing.Small))
    }
    leadingIcon?.let {
        it()
        Spacer(Modifier.width(Spacing.XSmall))
    }
    Text(text = text, style = textStyle)
}

@Preview
@Composable
private fun AppButtonPrimaryPreview() {
    AppButton(text = "Primary Button", onClick = {})
}

@Preview
@Composable
private fun AppButtonSecondaryPreview() {
    AppButton(text = "Secondary", onClick = {}, variant = ButtonVariant.Secondary)
}

@Preview
@Composable
private fun AppButtonGhostPreview() {
    AppButton(text = "Ghost", onClick = {}, variant = ButtonVariant.Ghost)
}

@Preview
@Composable
private fun AppButtonDangerPreview() {
    AppButton(text = "Delete", onClick = {}, variant = ButtonVariant.Danger)
}
