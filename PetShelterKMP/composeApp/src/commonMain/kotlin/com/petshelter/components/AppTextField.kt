package com.petshelter.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.Radii

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "",
    label: String? = null,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(placeholder) },
        label = label?.let { { Text(it) } },
        singleLine = singleLine,
        enabled = enabled,
        trailingIcon = trailingIcon,
        shape = RoundedCornerShape(Radii.Medium),
        colors =
            OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PetShelterTheme.colors.BorderFocus,
                unfocusedBorderColor = PetShelterTheme.colors.Border,
                focusedContainerColor = PetShelterTheme.colors.BackgroundPrimary,
                unfocusedContainerColor = PetShelterTheme.colors.BackgroundPrimary,
            ),
    )
}

@Preview
@Composable
private fun AppTextFieldPreview() {
    AppTextField(
        value = "",
        onValueChange = {},
        placeholder = "Enter text...",
    )
}
