package ru.practicum.android.diploma.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.feature.favorite.presentation.FavoriteFragmentViewModel
import ru.practicum.android.diploma.feature.filter.presentation.FilterCountryViewModel
import ru.practicum.android.diploma.feature.filter.presentation.FilterRegionViewmodel
import ru.practicum.android.diploma.feature.filter.presentation.FilterSettingsViewModel
import ru.practicum.android.diploma.feature.filter.presentation.IndustryViewModel
import ru.practicum.android.diploma.feature.filter.presentation.LocationViewModel
import ru.practicum.android.diploma.feature.search.presentation.SearchViewModel
import ru.practicum.android.diploma.feature.vacancy.presentation.VacancyViewModel

val viewModelModule = module {
    viewModel {
        SearchViewModel(get(), get())
    }
    viewModel {
        FavoriteFragmentViewModel(get(), get())
    }
    viewModel {
        VacancyViewModel(get(), get())
    }
    viewModel {
        FilterRegionViewmodel(get())
    }
    viewModel {
        IndustryViewModel(get(), get())
    }
    viewModel {
        FilterCountryViewModel(get())
    }

    viewModel {
        LocationViewModel(get(), get())
    }
    viewModel {
        FilterSettingsViewModel(get(), get(), get())
    }
}
