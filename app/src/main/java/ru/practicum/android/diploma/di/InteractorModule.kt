package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.feature.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.feature.vacancy.domain.impl.VacancyInteractorImpl
import ru.practicum.android.diploma.feature.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.feature.search.domain.impl.SearchInteractorImpl

val interactorModule = module {
    single<VacancyInteractor> {
        VacancyInteractorImpl(get())
    }

    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }
}
