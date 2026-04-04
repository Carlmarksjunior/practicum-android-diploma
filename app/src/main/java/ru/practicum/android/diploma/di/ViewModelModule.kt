package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.feature.favorite.presentation.FavoriteFragmentViewModel
import ru.practicum.android.diploma.feature.search.presentation.SearchViewModel
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyViewModel

val viewModelModule = module {
    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        FavoriteFragmentViewModel(get(), get())
    }
    viewModel {
        VacancyViewModel(get(), get())
    }
}
