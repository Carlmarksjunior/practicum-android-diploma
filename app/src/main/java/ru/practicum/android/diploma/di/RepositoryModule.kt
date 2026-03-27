package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.feature.search.data.impl.SearchRepositoryImpl
import ru.practicum.android.diploma.feature.search.domain.api.SearchRepository
import ru.practicum.android.diploma.feature.vacancy.data.impl.VacancyDetailMapper
import ru.practicum.android.diploma.feature.vacancy.data.impl.VacancyRepositoryImpl
import ru.practicum.android.diploma.feature.vacancy.domain.api.VacancyRepository

val repositoryModule = module {
    single<SearchRepository> {
        SearchRepositoryImpl(get())
    }
    single<VacancyRepository> {
        VacancyRepositoryImpl(get(), get(), get())
    }

    single {
        VacancyDetailMapper()
    }
}
