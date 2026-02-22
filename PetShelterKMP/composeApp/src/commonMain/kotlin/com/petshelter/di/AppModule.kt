package com.petshelter.di

import com.petshelter.core.data.AnimalRepositoryImpl
import com.petshelter.core.model.AnimalType
import com.petshelter.core.repository.AnimalRepository
import com.petshelter.feature.animals.AnimalDetailViewModel
import com.petshelter.feature.animals.AnimalListViewModel
import com.petshelter.feature.questionnaire.QuestionnaireViewModel
import com.petshelter.util.DefaultExternalNavigator
import com.petshelter.util.ExternalNavigator
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule =
    module {
        single<AnimalRepository> { AnimalRepositoryImpl() }
        single<ExternalNavigator> { DefaultExternalNavigator() }
        viewModel { (animalType: AnimalType?) -> AnimalListViewModel(animalType, get()) }
        viewModel { (animalId: String) -> AnimalDetailViewModel(animalId, get()) }
        viewModel { QuestionnaireViewModel(get()) }
    }
