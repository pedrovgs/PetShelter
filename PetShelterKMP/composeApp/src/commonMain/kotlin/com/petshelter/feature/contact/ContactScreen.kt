package com.petshelter.feature.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petshelter.components.SectionCard
import com.petshelter.components.SectionTitle
import com.petshelter.components.SocialBadge
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Radii
import com.petshelter.designsystem.Spacing
import com.petshelter.designsystem.icons.PetShelterIcons
import org.jetbrains.compose.resources.stringResource
import petshelter.composeapp.generated.resources.Res
import petshelter.composeapp.generated.resources.center_info_address
import petshelter.composeapp.generated.resources.center_info_email
import petshelter.composeapp.generated.resources.center_info_phone
import petshelter.composeapp.generated.resources.contact_address_label
import petshelter.composeapp.generated.resources.contact_direct_title
import petshelter.composeapp.generated.resources.contact_email_label
import petshelter.composeapp.generated.resources.contact_map_placeholder
import petshelter.composeapp.generated.resources.contact_map_title
import petshelter.composeapp.generated.resources.contact_phone_label
import petshelter.composeapp.generated.resources.contact_social_title
import petshelter.composeapp.generated.resources.contact_title

@Composable
fun ContactScreen(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(Spacing.Large),
        verticalArrangement = Arrangement.spacedBy(Spacing.Large),
    ) {
        PageHeader()
        DirectContactSection()
        MapSection()
        SocialMediaSection()
        Spacer(Modifier.height(Spacing.Large))
    }
}

@Composable
private fun PageHeader() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "\uD83D\uDCDE",
            fontSize = 48.sp,
        )
        Spacer(Modifier.height(Spacing.Small))
        Text(
            text = stringResource(Res.string.contact_title),
            style = PetShelterTypography.Heading1,
            color = PetShelterTheme.colors.TextPrimary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun DirectContactSection() {
    SectionCard {
        SectionTitle(emoji = "\uD83D\uDCCD", title = stringResource(Res.string.contact_direct_title))
        Spacer(Modifier.height(Spacing.Medium))
        ContactDetailRow(
            icon = PetShelterIcons.Building,
            label = stringResource(Res.string.contact_address_label),
            value = stringResource(Res.string.center_info_address),
        )
        Spacer(Modifier.height(Spacing.Medium))
        ContactDetailRow(
            icon = PetShelterIcons.Phone,
            label = stringResource(Res.string.contact_phone_label),
            value = stringResource(Res.string.center_info_phone),
        )
        Spacer(Modifier.height(Spacing.Medium))
        ContactDetailRow(
            icon = PetShelterIcons.Star,
            label = stringResource(Res.string.contact_email_label),
            value = stringResource(Res.string.center_info_email),
        )
    }
}

@Composable
private fun MapSection() {
    SectionCard {
        SectionTitle(emoji = "\uD83D\uDDFA\uFE0F", title = stringResource(Res.string.contact_map_title))
        Spacer(Modifier.height(Spacing.Medium))
        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(Radii.Medium))
                    .background(PetShelterTheme.colors.BackgroundTertiary),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "\uD83D\uDCCD",
                    fontSize = 32.sp,
                )
                Spacer(Modifier.height(Spacing.Small))
                Text(
                    text = stringResource(Res.string.contact_map_placeholder),
                    style = PetShelterTypography.BodySmall,
                    color = PetShelterTheme.colors.TextSecondary,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun SocialMediaSection() {
    SectionCard {
        SectionTitle(emoji = "\uD83D\uDCF1", title = stringResource(Res.string.contact_social_title))
        Spacer(Modifier.height(Spacing.Medium))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Spacing.Medium),
        ) {
            SocialBadge(text = "Facebook")
            SocialBadge(text = "Twitter")
            SocialBadge(text = "YouTube")
            SocialBadge(text = "Pinterest")
        }
    }
}

@Composable
private fun ContactDetailRow(
    icon: ImageVector,
    label: String,
    value: String,
) {
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = PetShelterTheme.colors.Primary,
        )
        Spacer(Modifier.width(Spacing.Small))
        Column {
            Text(
                text = label,
                style = PetShelterTypography.Label,
                color = PetShelterTheme.colors.TextSecondary,
            )
            Spacer(Modifier.height(Spacing.XXSmall))
            Text(
                text = value,
                style = PetShelterTypography.Body,
                color = PetShelterTheme.colors.TextPrimary,
            )
        }
    }
}

@Preview
@Composable
private fun ContactScreenPreview() {
    PetShelterTheme {
        ContactScreen()
    }
}
