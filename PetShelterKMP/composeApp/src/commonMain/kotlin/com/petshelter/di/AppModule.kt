package com.petshelter.di

import com.petshelter.core.data.AnimalRepository
import com.petshelter.core.model.AnimalType
import com.petshelter.feature.animals.AnimalDetailViewModel
import com.petshelter.feature.animals.AnimalListViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule =
    module {
        single { AnimalRepository() }
        viewModel { (animalType: AnimalType) -> AnimalListViewModel(animalType, get()) }
        viewModel { (animalId: String) -> AnimalDetailViewModel(animalId, get()) }
    }
