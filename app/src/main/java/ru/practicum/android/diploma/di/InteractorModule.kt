package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.feature.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.feature.search.domain.impl.SearchInteractorImpl

val interactorModule = module {
    single<SearchInteractor> {
        SearchInteractorImpl(get())
    }
}
