package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.feature.favorite.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.feature.favorite.domain.impl.FavoriteInteractorImpl
import ru.practicum.android.diploma.feature.filter.domain.api.IndustryInteractor
import ru.practicum.android.diploma.feature.filter.domain.api.IndustrySaveInteractor
import ru.practicum.android.diploma.feature.filter.domain.impl.IndustryInteractorImpl
import ru.practicum.android.diploma.feature.filter.domain.impl.IndustrySaveInteractorImpl
import ru.practicum.android.diploma.feature.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.feature.search.domain.impl.SearchInteractorImpl
import ru.practicum.android.diploma.feature.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.feature.vacancy.domain.impl.VacancyInteractorImpl

val interactorModule = module {
    single<VacancyInteractor> {
        VacancyInteractorImpl(get())
    }

    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }

    single<FavoriteInteractor> {
        FavoriteInteractorImpl(get())
    }

    single<IndustryInteractor> {
        IndustryInteractorImpl(get())
    }

    single<IndustrySaveInteractor> {
        IndustrySaveInteractorImpl(get())
    }

}
