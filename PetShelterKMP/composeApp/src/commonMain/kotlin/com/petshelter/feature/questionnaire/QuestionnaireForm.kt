package com.petshelter.feature.questionnaire

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.petshelter.components.AppButton
import com.petshelter.components.ButtonSize
import com.petshelter.components.ButtonVariant
import com.petshelter.designsystem.PetShelterTheme
import com.petshelter.designsystem.PetShelterTypography
import com.petshelter.designsystem.Radii
import com.petshelter.designsystem.Spacing
import org.jetbrains.compose.resources.stringResource
import petshelter.composeapp.generated.resources.Res
import petshelter.composeapp.generated.resources.questionnaire_activity_high
import petshelter.composeapp.generated.resources.questionnaire_activity_low
import petshelter.composeapp.generated.resources.questionnaire_activity_moderate
import petshelter.composeapp.generated.resources.questionnaire_affection_affectionate
import petshelter.composeapp.generated.resources.questionnaire_affection_balanced
import petshelter.composeapp.generated.resources.questionnaire_affection_shy
import petshelter.composeapp.generated.resources.questionnaire_alone_few_hours
import petshelter.composeapp.generated.resources.questionnaire_alone_full_day
import petshelter.composeapp.generated.resources.questionnaire_alone_half_day
import petshelter.composeapp.generated.resources.questionnaire_animal_cat
import petshelter.composeapp.generated.resources.questionnaire_animal_dog
import petshelter.composeapp.generated.resources.questionnaire_animal_either
import petshelter.composeapp.generated.resources.questionnaire_mandatory_notice
import petshelter.composeapp.generated.resources.questionnaire_no
import petshelter.composeapp.generated.resources.questionnaire_q_activity
import petshelter.composeapp.generated.resources.questionnaire_q_affection
import petshelter.composeapp.generated.resources.questionnaire_q_alone_time
import petshelter.composeapp.generated.resources.questionnaire_q_animal_type
import petshelter.composeapp.generated.resources.questionnaire_q_kids
import petshelter.composeapp.generated.resources.questionnaire_q_other_animals
import petshelter.composeapp.generated.resources.questionnaire_q_size
import petshelter.composeapp.generated.resources.questionnaire_q_special_needs
import petshelter.composeapp.generated.resources.questionnaire_q_tricks
import petshelter.composeapp.generated.resources.questionnaire_q_walk_time
import petshelter.composeapp.generated.resources.questionnaire_size_any
import petshelter.composeapp.generated.resources.questionnaire_size_large
import petshelter.composeapp.generated.resources.questionnaire_size_medium
import petshelter.composeapp.generated.resources.questionnaire_size_small
import petshelter.composeapp.generated.resources.questionnaire_special_maybe
import petshelter.composeapp.generated.resources.questionnaire_special_no
import petshelter.composeapp.generated.resources.questionnaire_special_yes
import petshelter.composeapp.generated.resources.questionnaire_submit
import petshelter.composeapp.generated.resources.questionnaire_subtitle
import petshelter.composeapp.generated.resources.questionnaire_title
import petshelter.composeapp.generated.resources.questionnaire_walk_long
import petshelter.composeapp.generated.resources.questionnaire_walk_moderate
import petshelter.composeapp.generated.resources.questionnaire_walk_short
import petshelter.composeapp.generated.resources.questionnaire_yes

@Composable
fun QuestionnaireForm(
    answers: QuestionnaireAnswers,
    onAnimalPreferenceChanged: (AnimalPreference) -> Unit,
    onSizePreferenceChanged: (SizePreference) -> Unit,
    onWalkTimeChanged: (WalkTimePreference) -> Unit,
    onAloneTimeChanged: (AloneTimePreference) -> Unit,
    onActivityLevelChanged: (ActivityLevel) -> Unit,
    onAffectionPreferenceChanged: (AffectionPreference) -> Unit,
    onHasOtherAnimalsChanged: (Boolean) -> Unit,
    onHasKidsChanged: (Boolean) -> Unit,
    onWantsToTeachTricksChanged: (Boolean) -> Unit,
    onSpecialNeedsChanged: (SpecialNeedsPreference) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = Spacing.Medium),
    ) {
        Spacer(Modifier.height(Spacing.Medium))

        Text(
            text = stringResource(Res.string.questionnaire_title),
            style = PetShelterTypography.Heading2,
            color = PetShelterTheme.colors.Primary,
        )
        Spacer(Modifier.height(Spacing.XSmall))
        Text(
            text = stringResource(Res.string.questionnaire_subtitle),
            style = PetShelterTypography.Body,
            color = PetShelterTheme.colors.TextSecondary,
        )
        Spacer(Modifier.height(Spacing.Small))
        Text(
            text = stringResource(Res.string.questionnaire_mandatory_notice),
            style = PetShelterTypography.Caption,
            color = PetShelterTheme.colors.Error,
        )

        Spacer(Modifier.height(Spacing.Medium))

        val progress =
            if (answers.totalQuestions > 0) {
                answers.answeredCount.toFloat() / answers.totalQuestions
            } else {
                0f
            }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(Radii.Full)),
            color = PetShelterTheme.colors.Primary,
            trackColor = PetShelterTheme.colors.TextSecondary,
        )

        Spacer(Modifier.height(Spacing.Large))

        var questionNumber = 1

        // Q1: Animal type
        QuestionSection(
            number = questionNumber++,
            question = stringResource(Res.string.questionnaire_q_animal_type),
        ) {
            OptionChips(
                options =
                    listOf(
                        AnimalPreference.DOG to stringResource(Res.string.questionnaire_animal_dog),
                        AnimalPreference.CAT to stringResource(Res.string.questionnaire_animal_cat),
                        AnimalPreference.EITHER to stringResource(Res.string.questionnaire_animal_either),
                    ),
                selected = answers.animalPreference,
                onSelected = onAnimalPreferenceChanged,
            )
        }

        // Q2: Size
        QuestionSection(
            number = questionNumber++,
            question = stringResource(Res.string.questionnaire_q_size),
        ) {
            OptionChips(
                options =
                    listOf(
                        SizePreference.SMALL to stringResource(Res.string.questionnaire_size_small),
                        SizePreference.MEDIUM to stringResource(Res.string.questionnaire_size_medium),
                        SizePreference.LARGE to stringResource(Res.string.questionnaire_size_large),
                        SizePreference.ANY to stringResource(Res.string.questionnaire_size_any),
                    ),
                selected = answers.sizePreference,
                onSelected = onSizePreferenceChanged,
            )
        }

        // Q3: Walk time (only for dogs)
        if (answers.animalPreference != AnimalPreference.CAT) {
            QuestionSection(
                number = questionNumber++,
                question = stringResource(Res.string.questionnaire_q_walk_time),
            ) {
                OptionChips(
                    options =
                        listOf(
                            WalkTimePreference.SHORT to stringResource(Res.string.questionnaire_walk_short),
                            WalkTimePreference.MODERATE to stringResource(Res.string.questionnaire_walk_moderate),
                            WalkTimePreference.LONG to stringResource(Res.string.questionnaire_walk_long),
                        ),
                    selected = answers.walkTime,
                    onSelected = onWalkTimeChanged,
                )
            }
        }

        // Q4: Alone time
        QuestionSection(
            number = questionNumber++,
            question = stringResource(Res.string.questionnaire_q_alone_time),
        ) {
            OptionChips(
                options =
                    listOf(
                        AloneTimePreference.FEW_HOURS to stringResource(Res.string.questionnaire_alone_few_hours),
                        AloneTimePreference.HALF_DAY to stringResource(Res.string.questionnaire_alone_half_day),
                        AloneTimePreference.FULL_DAY to stringResource(Res.string.questionnaire_alone_full_day),
                    ),
                selected = answers.aloneTime,
                onSelected = onAloneTimeChanged,
            )
        }

        // Q5: Activity level
        QuestionSection(
            number = questionNumber++,
            question = stringResource(Res.string.questionnaire_q_activity),
        ) {
            OptionChips(
                options =
                    listOf(
                        ActivityLevel.LOW to stringResource(Res.string.questionnaire_activity_low),
                        ActivityLevel.MODERATE to stringResource(Res.string.questionnaire_activity_moderate),
                        ActivityLevel.HIGH to stringResource(Res.string.questionnaire_activity_high),
                    ),
                selected = answers.activityLevel,
                onSelected = onActivityLevelChanged,
            )
        }

        // Q6: Affection
        QuestionSection(
            number = questionNumber++,
            question = stringResource(Res.string.questionnaire_q_affection),
        ) {
            OptionChips(
                options =
                    listOf(
                        AffectionPreference.SHY to stringResource(Res.string.questionnaire_affection_shy),
                        AffectionPreference.BALANCED to stringResource(Res.string.questionnaire_affection_balanced),
                        AffectionPreference.AFFECTIONATE to stringResource(Res.string.questionnaire_affection_affectionate),
                    ),
                selected = answers.affectionPreference,
                onSelected = onAffectionPreferenceChanged,
            )
        }

        // Q7: Other animals
        QuestionSection(
            number = questionNumber++,
            question = stringResource(Res.string.questionnaire_q_other_animals),
        ) {
            OptionChips(
                options =
                    listOf(
                        true to stringResource(Res.string.questionnaire_yes),
                        false to stringResource(Res.string.questionnaire_no),
                    ),
                selected = answers.hasOtherAnimals,
                onSelected = onHasOtherAnimalsChanged,
            )
        }

        // Q8: Kids
        QuestionSection(
            number = questionNumber++,
            question = stringResource(Res.string.questionnaire_q_kids),
        ) {
            OptionChips(
                options =
                    listOf(
                        true to stringResource(Res.string.questionnaire_yes),
                        false to stringResource(Res.string.questionnaire_no),
                    ),
                selected = answers.hasKids,
                onSelected = onHasKidsChanged,
            )
        }

        // Q9: Teach tricks
        QuestionSection(
            number = questionNumber++,
            question = stringResource(Res.string.questionnaire_q_tricks),
        ) {
            OptionChips(
                options =
                    listOf(
                        true to stringResource(Res.string.questionnaire_yes),
                        false to stringResource(Res.string.questionnaire_no),
                    ),
                selected = answers.wantsToTeachTricks,
                onSelected = onWantsToTeachTricksChanged,
            )
        }

        // Q10: Special needs
        @Suppress("UNUSED_CHANGED_VALUE")
        QuestionSection(
            number = questionNumber++,
            question = stringResource(Res.string.questionnaire_q_special_needs),
        ) {
            OptionChips(
                options =
                    listOf(
                        SpecialNeedsPreference.YES to stringResource(Res.string.questionnaire_special_yes),
                        SpecialNeedsPreference.MAYBE to stringResource(Res.string.questionnaire_special_maybe),
                        SpecialNeedsPreference.NO to stringResource(Res.string.questionnaire_special_no),
                    ),
                selected = answers.specialNeeds,
                onSelected = onSpecialNeedsChanged,
            )
        }

        Spacer(Modifier.height(Spacing.Large))

        AppButton(
            text = stringResource(Res.string.questionnaire_submit),
            onClick = onSubmit,
            variant = ButtonVariant.Primary,
            size = ButtonSize.Large,
            enabled = answers.isComplete,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(Spacing.XLarge))
    }
}

@Composable
private fun QuestionSection(
    number: Int,
    question: String,
    content: @Composable () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "$number. $question",
            style = PetShelterTypography.Body,
            color = PetShelterTheme.colors.Primary,
        )
        Spacer(Modifier.height(Spacing.Small))
        content()
        Spacer(Modifier.height(Spacing.Medium))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun <T> OptionChips(
    options: List<Pair<T, String>>,
    selected: T?,
    onSelected: (T) -> Unit,
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(Spacing.Small),
        verticalArrangement = Arrangement.spacedBy(Spacing.Small),
    ) {
        options.forEach { (value, label) ->
            OptionChip(
                label = label,
                isSelected = value == selected,
                onClick = { onSelected(value) },
            )
        }
    }
}

@Composable
private fun OptionChip(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        if (isSelected) {
            PetShelterTheme.colors.Primary
        } else {
            PetShelterTheme.colors.BackgroundTertiary
        }
    val textColor =
        if (isSelected) {
            PetShelterTheme.colors.TextInverse
        } else {
            PetShelterTheme.colors.TextPrimary
        }

    Text(
        text = label,
        style = PetShelterTypography.Caption,
        color = textColor,
        modifier =
            modifier
                .clip(RoundedCornerShape(Radii.Full))
                .background(backgroundColor)
                .clickable(onClick = onClick)
                .padding(horizontal = Spacing.Medium, vertical = Spacing.Small),
    )
}

@Preview
@Composable
private fun QuestionnaireFormPreview() {
    PetShelterTheme {
        QuestionnaireForm(
            answers = QuestionnaireAnswers(),
            onAnimalPreferenceChanged = {},
            onSizePreferenceChanged = {},
            onWalkTimeChanged = {},
            onAloneTimeChanged = {},
            onActivityLevelChanged = {},
            onAffectionPreferenceChanged = {},
            onHasOtherAnimalsChanged = {},
            onHasKidsChanged = {},
            onWantsToTeachTricksChanged = {},
            onSpecialNeedsChanged = {},
            onSubmit = {},
        )
    }
}

@Preview
@Composable
private fun QuestionnaireFormPartialPreview() {
    PetShelterTheme {
        QuestionnaireForm(
            answers =
                QuestionnaireAnswers(
                    animalPreference = AnimalPreference.DOG,
                    sizePreference = SizePreference.MEDIUM,
                    walkTime = WalkTimePreference.MODERATE,
                ),
            onAnimalPreferenceChanged = {},
            onSizePreferenceChanged = {},
            onWalkTimeChanged = {},
            onAloneTimeChanged = {},
            onActivityLevelChanged = {},
            onAffectionPreferenceChanged = {},
            onHasOtherAnimalsChanged = {},
            onHasKidsChanged = {},
            onWantsToTeachTricksChanged = {},
            onSpecialNeedsChanged = {},
            onSubmit = {},
        )
    }
}
