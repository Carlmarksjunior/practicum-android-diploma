package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.feature.favorite.presentation.FavoriteFragmentViewModel

val viewModelModule = module {

    viewModel {
        FavoriteFragmentViewModel(get())
    }
}
