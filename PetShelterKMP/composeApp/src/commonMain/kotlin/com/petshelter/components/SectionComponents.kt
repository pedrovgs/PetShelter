package com.petshelter.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Radii
import com.petshelter.designsystem.Spacing

@Composable
internal fun SectionCard(content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Radii.Large),
        colors =
            CardDefaults.cardColors(
                containerColor = PetShelterTheme.colors.BackgroundSecondary,
            ),
    ) {
        Column(modifier = Modifier.padding(Spacing.Large)) {
            content()
        }
    }
}

@Composable
internal fun SectionTitle(
    emoji: String,
    title: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = emoji, fontSize = 24.sp)
        Spacer(Modifier.width(Spacing.Small))
        Text(
            text = title,
            style = PetShelterTypography.Heading2,
            color = PetShelterTheme.colors.TextPrimary,
        )
    }
}

@Composable
internal fun SocialBadge(text: String) {
    Box(
        modifier =
            Modifier
                .clip(RoundedCornerShape(Radii.Full))
                .background(PetShelterTheme.colors.Primary)
                .padding(horizontal = Spacing.Medium, vertical = Spacing.XSmall),
    ) {
        Text(
            text = text,
            style = PetShelterTypography.Caption,
            color = PetShelterTheme.colors.TextInverse,
        )
    }
}
