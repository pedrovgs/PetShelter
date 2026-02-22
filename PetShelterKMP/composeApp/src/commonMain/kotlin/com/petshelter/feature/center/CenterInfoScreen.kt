package com.petshelter.feature.center

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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Radii
import com.petshelter.designsystem.Spacing
import com.petshelter.designsystem.icons.PetShelterIcons
import org.jetbrains.compose.resources.stringResource
import petshelter.composeapp.generated.resources.Res
import petshelter.composeapp.generated.resources.center_info_about_description
import petshelter.composeapp.generated.resources.center_info_about_title
import petshelter.composeapp.generated.resources.center_info_address
import petshelter.composeapp.generated.resources.center_info_email
import petshelter.composeapp.generated.resources.center_info_help_adopt
import petshelter.composeapp.generated.resources.center_info_help_donate
import petshelter.composeapp.generated.resources.center_info_help_foster
import petshelter.composeapp.generated.resources.center_info_help_spread
import petshelter.composeapp.generated.resources.center_info_help_title
import petshelter.composeapp.generated.resources.center_info_help_volunteer
import petshelter.composeapp.generated.resources.center_info_location_title
import petshelter.composeapp.generated.resources.center_info_mission_description
import petshelter.composeapp.generated.resources.center_info_mission_title
import petshelter.composeapp.generated.resources.center_info_organization
import petshelter.composeapp.generated.resources.center_info_phone
import petshelter.composeapp.generated.resources.center_info_service_adoption
import petshelter.composeapp.generated.resources.center_info_service_education
import petshelter.composeapp.generated.resources.center_info_service_rescue
import petshelter.composeapp.generated.resources.center_info_service_sterilization
import petshelter.composeapp.generated.resources.center_info_service_veterinary
import petshelter.composeapp.generated.resources.center_info_services_title
import petshelter.composeapp.generated.resources.center_info_social_description
import petshelter.composeapp.generated.resources.center_info_social_title
import petshelter.composeapp.generated.resources.center_info_title
import petshelter.composeapp.generated.resources.center_info_visiting_note
import petshelter.composeapp.generated.resources.center_info_visiting_title
import petshelter.composeapp.generated.resources.center_info_visiting_weekdays
import petshelter.composeapp.generated.resources.center_info_visiting_weekends

@Composable
fun CenterInfoScreen(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(Spacing.Large),
        verticalArrangement = Arrangement.spacedBy(Spacing.Large),
    ) {
        PageHeader()
        AboutSection()
        MissionSection()
        ServicesSection()
        VisitingHoursSection()
        LocationSection()
        HowToHelpSection()
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
            text = "\uD83C\uDFE0",
            fontSize = 48.sp,
        )
        Spacer(Modifier.height(Spacing.Small))
        Text(
            text = stringResource(Res.string.center_info_title),
            style = PetShelterTypography.Heading1,
            color = PetShelterTheme.colors.TextPrimary,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(Spacing.XSmall))
        Text(
            text = stringResource(Res.string.center_info_organization),
            style = PetShelterTypography.Body,
            color = PetShelterTheme.colors.TextSecondary,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun AboutSection() {
    SectionCard {
        SectionTitle(emoji = "\uD83D\uDC3E", title = stringResource(Res.string.center_info_about_title))
        Spacer(Modifier.height(Spacing.Medium))
        Text(
            text = stringResource(Res.string.center_info_about_description),
            style = PetShelterTypography.Body,
            color = PetShelterTheme.colors.TextPrimary,
        )
    }
}

@Composable
private fun MissionSection() {
    SectionCard {
        SectionTitle(emoji = "\uD83C\uDF1F", title = stringResource(Res.string.center_info_mission_title))
        Spacer(Modifier.height(Spacing.Medium))
        Text(
            text = stringResource(Res.string.center_info_mission_description),
            style = PetShelterTypography.Body,
            color = PetShelterTheme.colors.TextPrimary,
        )
    }
}

@Composable
private fun ServicesSection() {
    SectionCard {
        SectionTitle(emoji = "\uD83D\uDEE0\uFE0F", title = stringResource(Res.string.center_info_services_title))
        Spacer(Modifier.height(Spacing.Medium))
        ServiceBulletItem(
            emoji = "\uD83D\uDE91",
            text = stringResource(Res.string.center_info_service_rescue),
        )
        ServiceBulletItem(
            emoji = "\uD83C\uDFE5",
            text = stringResource(Res.string.center_info_service_veterinary),
        )
        ServiceBulletItem(
            emoji = "\u2764\uFE0F",
            text = stringResource(Res.string.center_info_service_adoption),
        )
        ServiceBulletItem(
            emoji = "\uD83D\uDCDA",
            text = stringResource(Res.string.center_info_service_education),
        )
        ServiceBulletItem(
            emoji = "\u2702\uFE0F",
            text = stringResource(Res.string.center_info_service_sterilization),
        )
    }
}

@Composable
private fun VisitingHoursSection() {
    SectionCard {
        SectionTitle(emoji = "\uD83D\uDD52", title = stringResource(Res.string.center_info_visiting_title))
        Spacer(Modifier.height(Spacing.Medium))
        ScheduleRow(
            label = stringResource(Res.string.center_info_visiting_weekdays),
        )
        Spacer(Modifier.height(Spacing.Small))
        ScheduleRow(
            label = stringResource(Res.string.center_info_visiting_weekends),
        )
        Spacer(Modifier.height(Spacing.Medium))
        HorizontalDivider(color = PetShelterTheme.colors.BorderLight)
        Spacer(Modifier.height(Spacing.Medium))
        Text(
            text = stringResource(Res.string.center_info_visiting_note),
            style = PetShelterTypography.BodySmall,
            color = PetShelterTheme.colors.TextSecondary,
        )
    }
}

@Composable
private fun LocationSection() {
    SectionCard {
        SectionTitle(emoji = "\uD83D\uDCCD", title = stringResource(Res.string.center_info_location_title))
        Spacer(Modifier.height(Spacing.Medium))
        ContactInfoRow(
            icon = PetShelterIcons.Building,
            text = stringResource(Res.string.center_info_address),
        )
        Spacer(Modifier.height(Spacing.Small))
        ContactInfoRow(
            icon = PetShelterIcons.Phone,
            text = stringResource(Res.string.center_info_phone),
        )
        Spacer(Modifier.height(Spacing.Small))
        ContactInfoRow(
            icon = PetShelterIcons.Star,
            text = stringResource(Res.string.center_info_email),
        )
    }
}

@Composable
private fun HowToHelpSection() {
    SectionCard {
        SectionTitle(emoji = "\uD83E\uDD1D", title = stringResource(Res.string.center_info_help_title))
        Spacer(Modifier.height(Spacing.Medium))
        ServiceBulletItem(
            emoji = "\uD83D\uDC36",
            text = stringResource(Res.string.center_info_help_adopt),
        )
        ServiceBulletItem(
            emoji = "\uD83D\uDE4B",
            text = stringResource(Res.string.center_info_help_volunteer),
        )
        ServiceBulletItem(
            emoji = "\uD83D\uDCB0",
            text = stringResource(Res.string.center_info_help_donate),
        )
        ServiceBulletItem(
            emoji = "\uD83C\uDFE0",
            text = stringResource(Res.string.center_info_help_foster),
        )
        ServiceBulletItem(
            emoji = "\uD83D\uDCE2",
            text = stringResource(Res.string.center_info_help_spread),
        )
    }
}

@Composable
private fun SocialMediaSection() {
    SectionCard {
        SectionTitle(emoji = "\uD83D\uDCF1", title = stringResource(Res.string.center_info_social_title))
        Spacer(Modifier.height(Spacing.Medium))
        Text(
            text = stringResource(Res.string.center_info_social_description),
            style = PetShelterTypography.Body,
            color = PetShelterTheme.colors.TextPrimary,
        )
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
private fun ServiceBulletItem(
    emoji: String,
    text: String,
) {
    Row(
        modifier = Modifier.padding(vertical = Spacing.XSmall),
        verticalAlignment = Alignment.Top,
    ) {
        Text(text = emoji, fontSize = 16.sp)
        Spacer(Modifier.width(Spacing.Small))
        Text(
            text = text,
            style = PetShelterTypography.Body,
            color = PetShelterTheme.colors.TextPrimary,
        )
    }
}

@Composable
private fun ScheduleRow(label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "\uD83D\uDCC5", fontSize = 16.sp)
        Spacer(Modifier.width(Spacing.Small))
        Text(
            text = label,
            style = PetShelterTypography.Body.copy(fontWeight = FontWeight.Medium),
            color = PetShelterTheme.colors.TextPrimary,
        )
    }
}

@Composable
private fun ContactInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = PetShelterTheme.colors.Primary,
        )
        Spacer(Modifier.width(Spacing.Small))
        Text(
            text = text,
            style = PetShelterTypography.Body,
            color = PetShelterTheme.colors.TextPrimary,
        )
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
private fun CenterInfoScreenPreview() {
    PetShelterTheme {
        CenterInfoScreen()
    }
}
