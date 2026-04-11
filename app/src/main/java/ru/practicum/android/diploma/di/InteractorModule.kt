package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.feature.favorite.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.feature.favorite.domain.impl.FavoriteInteractorImpl
import ru.practicum.android.diploma.feature.filter.domain.api.FilterCountryInteractor
import ru.practicum.android.diploma.feature.filter.domain.api.FilterSettingsInteractor
import ru.practicum.android.diploma.feature.filter.domain.api.IndustryInteractor
import ru.practicum.android.diploma.feature.filter.domain.api.IndustrySaveInteractor
import ru.practicum.android.diploma.feature.filter.domain.api.LocationInteractor
import ru.practicum.android.diploma.feature.filter.domain.api.regions.FilterRegionsInteractor
import ru.practicum.android.diploma.feature.filter.domain.impl.FilterCountryInteractorImpl
import ru.practicum.android.diploma.feature.filter.domain.impl.FilterRegionInteractorImpl
import ru.practicum.android.diploma.feature.filter.domain.impl.FilterSettingsInteractorImpl
import ru.practicum.android.diploma.feature.filter.domain.impl.IndustryInteractorImpl
import ru.practicum.android.diploma.feature.filter.domain.impl.IndustrySaveInteractorImpl
import ru.practicum.android.diploma.feature.filter.domain.impl.LocationInteractorImpl
import ru.practicum.android.diploma.feature.search.domain.api.PaginationInteractor
import ru.practicum.android.diploma.feature.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.feature.search.domain.impl.PaginationInteractorImpl
import ru.practicum.android.diploma.feature.search.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.feature.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.feature.vacancy.domain.impl.VacancyInteractorImpl

val interactorModule = module {
    single<VacancyInteractor> {
        VacancyInteractorImpl(get())
    }

    single<SearchInteractor> {
        SearchInteractorImpl(get(), get())
    }

    single<FavoriteInteractor> {
        FavoriteInteractorImpl(get())
    }

    single<IndustryInteractor> {
        IndustryInteractorImpl(get(), get())
    }

    single<IndustrySaveInteractor> {
        IndustrySaveInteractorImpl(get())
    }

    single<FilterCountryInteractor> {
        FilterCountryInteractorImpl(get(), get())
    }

    single<FilterRegionsInteractor> {
        FilterRegionInteractorImpl(get(), get(), get())
    }

    single<LocationInteractor> {
        LocationInteractorImpl(get(), get())
    }

    single<FilterSettingsInteractor> {
        FilterSettingsInteractorImpl(get(), get())
    }

    factory<PaginationInteractor> {
        PaginationInteractorImpl(get())
    }
}
