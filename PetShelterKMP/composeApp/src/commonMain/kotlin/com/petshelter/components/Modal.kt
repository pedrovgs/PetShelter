package com.petshelter.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Radii
import com.petshelter.designsystem.Spacing

@Composable
fun Modal(
    onDismiss: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    actions: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = modifier,
            shape = RoundedCornerShape(Radii.XLarge),
            color = PetShelterTheme.colors.BackgroundPrimary,
            shadowElevation = com.petshelter.designsystem.Elevation.Large,
        ) {
            Column(modifier = Modifier.padding(Spacing.Large)) {
                Text(
                    text = title,
                    style = PetShelterTypography.Heading3,
                    color = PetShelterTheme.colors.TextPrimary,
                )
                Spacer(Modifier.height(Spacing.Medium))
                content()
                if (actions != null) {
                    Spacer(Modifier.height(Spacing.Large))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(Modifier.weight(1f))
                        actions()
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun ModalPreview() {
    Modal(
        onDismiss = {},
        title = "Confirm Action",
        actions = {
            AppButton("Cancel", onClick = {}, variant = ButtonVariant.Ghost)
            Spacer(Modifier.width(Spacing.Small))
            AppButton("Confirm", onClick = {})
        },
    ) {
        Text("Are you sure you want to proceed?")
    }
}
