package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.feature.filter.domain.api.ClearFiltersUseCase
import ru.practicum.android.diploma.feature.filter.domain.api.DeleteFilterByKeyUseCase
import ru.practicum.android.diploma.feature.filter.domain.impl.ClearFiltersUseCaseImpl
import ru.practicum.android.diploma.feature.filter.domain.impl.DeleteFilterByKeyUseCaseImpl

val useCaseModule = module {
    single<DeleteFilterByKeyUseCase> {
        DeleteFilterByKeyUseCaseImpl(get())
    }
    single<ClearFiltersUseCase> {
        ClearFiltersUseCaseImpl(get())
    }
}
