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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petshelter.components.AppButton
import com.petshelter.components.AppTextField
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
import petshelter.composeapp.generated.resources.contact_form_email
import petshelter.composeapp.generated.resources.contact_form_message
import petshelter.composeapp.generated.resources.contact_form_name
import petshelter.composeapp.generated.resources.contact_form_send
import petshelter.composeapp.generated.resources.contact_form_subject
import petshelter.composeapp.generated.resources.contact_form_title
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
        ContactFormSection()
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
private fun ContactFormSection() {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    SectionCard {
        SectionTitle(emoji = "\u2709\uFE0F", title = stringResource(Res.string.contact_form_title))
        Spacer(Modifier.height(Spacing.Medium))
        AppTextField(
            value = name,
            onValueChange = { name = it },
            placeholder = stringResource(Res.string.contact_form_name),
            label = stringResource(Res.string.contact_form_name),
        )
        Spacer(Modifier.height(Spacing.Small))
        AppTextField(
            value = email,
            onValueChange = { email = it },
            placeholder = stringResource(Res.string.contact_form_email),
            label = stringResource(Res.string.contact_form_email),
        )
        Spacer(Modifier.height(Spacing.Small))
        AppTextField(
            value = subject,
            onValueChange = { subject = it },
            placeholder = stringResource(Res.string.contact_form_subject),
            label = stringResource(Res.string.contact_form_subject),
        )
        Spacer(Modifier.height(Spacing.Small))
        AppTextField(
            value = message,
            onValueChange = { message = it },
            placeholder = stringResource(Res.string.contact_form_message),
            label = stringResource(Res.string.contact_form_message),
            singleLine = false,
            modifier = Modifier.height(120.dp),
        )
        Spacer(Modifier.height(Spacing.Medium))
        AppButton(
            text = stringResource(Res.string.contact_form_send),
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
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
private fun SectionCard(content: @Composable () -> Unit) {
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
private fun SectionTitle(
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

@Composable
private fun SocialBadge(text: String) {
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

@Preview
@Composable
private fun ContactScreenPreview() {
    PetShelterTheme {
        ContactScreen()
    }
}
