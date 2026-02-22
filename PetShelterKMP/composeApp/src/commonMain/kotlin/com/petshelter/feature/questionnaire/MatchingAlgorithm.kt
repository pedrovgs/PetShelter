package com.petshelter.feature.questionnaire

import com.petshelter.core.model.Animal
import com.petshelter.core.usecase.MatchAnimalsUseCase

private val matchAnimalsUseCase = MatchAnimalsUseCase()

fun computeMatches(
    answers: QuestionnaireAnswers,
    animals: List<Animal>,
): List<MatchedAnimal> = matchAnimalsUseCase(answers, animals)
