package com.petshelter.designsystem

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object PetShelterTypography {
    val Heading1 =
        TextStyle(
            fontSize = 32.sp,
            lineHeight = 40.sp,
            fontWeight = FontWeight.Bold,
        )

    val Heading2 =
        TextStyle(
            fontSize = 24.sp,
            lineHeight = 32.sp,
            fontWeight = FontWeight.SemiBold,
        )

    val Heading3 =
        TextStyle(
            fontSize = 20.sp,
            lineHeight = 28.sp,
            fontWeight = FontWeight.SemiBold,
        )

    val Body =
        TextStyle(
            fontSize = 16.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.Normal,
        )

    val BodySmall =
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Normal,
        )

    val Caption =
        TextStyle(
            fontSize = 12.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.Normal,
        )

    val Label =
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Medium,
        )

    val Button =
        TextStyle(
            fontSize = 14.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Medium,
        )
}

val AppTypography =
    Typography(
        displayLarge = PetShelterTypography.Heading1,
        displayMedium = PetShelterTypography.Heading2,
        displaySmall = PetShelterTypography.Heading3,
        headlineLarge = PetShelterTypography.Heading1,
        headlineMedium = PetShelterTypography.Heading2,
        headlineSmall = PetShelterTypography.Heading3,
        titleLarge = PetShelterTypography.Heading3,
        titleMedium =
            TextStyle(
                fontSize = 18.sp,
                lineHeight = 24.sp,
                fontWeight = FontWeight.Medium,
            ),
        titleSmall = PetShelterTypography.Label,
        bodyLarge = PetShelterTypography.Body,
        bodyMedium = PetShelterTypography.BodySmall,
        bodySmall = PetShelterTypography.Caption,
        labelLarge = PetShelterTypography.Button,
        labelMedium = PetShelterTypography.Label,
        labelSmall = PetShelterTypography.Caption,
    )
