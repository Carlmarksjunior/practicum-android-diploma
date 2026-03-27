package ru.practicum.android.diploma.di

import org.koin.dsl.module
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
}
