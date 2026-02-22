package com.petshelter.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Radii
import com.petshelter.designsystem.Spacing
import kotlinx.coroutines.delay

enum class ToastType { Success, Error, Info }

data class ToastState(
    val message: String = "",
    val type: ToastType = ToastType.Info,
    val visible: Boolean = false,
)

@Composable
fun Toast(
    state: ToastState,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isVisible by remember { mutableStateOf(state.visible) }

    LaunchedEffect(state.visible) {
        if (state.visible) {
            isVisible = true
            delay(3000)
            isVisible = false
            delay(300)
            onDismiss()
        } else {
            isVisible = false
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
        ) {
            val backgroundColor =
                when (state.type) {
                    ToastType.Success -> PetShelterTheme.colors.Success
                    ToastType.Error -> PetShelterTheme.colors.Error
                    ToastType.Info -> PetShelterTheme.colors.Theme
                }

            Surface(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(Spacing.Medium),
                shape = RoundedCornerShape(Radii.Medium),
                color = backgroundColor,
            ) {
                Text(
                    text = state.message,
                    modifier =
                        Modifier.padding(
                            horizontal = Spacing.Medium,
                            vertical = Spacing.Small,
                        ),
                    style = PetShelterTypography.BodySmall,
                    color = PetShelterTheme.colors.TextInverse,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ToastSuccessPreview() {
    Toast(
        state = ToastState(message = "Document saved successfully!", type = ToastType.Success, visible = true),
        onDismiss = {},
    )
}

@Preview
@Composable
private fun ToastErrorPreview() {
    Toast(
        state = ToastState(message = "Failed to save document", type = ToastType.Error, visible = true),
        onDismiss = {},
    )
}

@Preview
@Composable
private fun ToastInfoPreview() {
    Toast(
        state = ToastState(message = "Syncing your documents...", type = ToastType.Info, visible = true),
        onDismiss = {},
    )
}
