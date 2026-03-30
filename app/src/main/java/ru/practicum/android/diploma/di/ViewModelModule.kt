package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyViewModel

val viewModelModule = module {
    viewModel {
        VacancyViewModel(get())
    }
}
