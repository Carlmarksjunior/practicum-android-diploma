package ru.practicum.android.diploma.di

import com.google.gson.reflect.TypeToken
import org.koin.dsl.module
import ru.practicum.android.diploma.db.data.FavoriteRepositoryImpl
import ru.practicum.android.diploma.feature.favorite.domain.api.FavoriteRepository
import ru.practicum.android.diploma.feature.filter.data.impl.FilterCountryRepositoryImpl
import ru.practicum.android.diploma.feature.filter.data.impl.FiltersDeletingRepositoryImpl
import ru.practicum.android.diploma.feature.filter.data.impl.FiltersGettingRepositoryImpl
import ru.practicum.android.diploma.feature.filter.data.impl.FiltersSavingRepositoryImpl
import ru.practicum.android.diploma.feature.filter.data.impl.IndustryRepositoryImpl
import ru.practicum.android.diploma.feature.filter.data.impl.regions.FilterRegionsRepositoryImpl
import ru.practicum.android.diploma.feature.filter.domain.api.FilterCountryRepository
import ru.practicum.android.diploma.feature.filter.domain.api.FiltersDeletingRepository
import ru.practicum.android.diploma.feature.filter.domain.api.FiltersGettingRepository
import ru.practicum.android.diploma.feature.filter.domain.api.FiltersSavingRepository
import ru.practicum.android.diploma.feature.filter.domain.api.IndustryRepository
import ru.practicum.android.diploma.feature.filter.domain.api.regions.FilterRegionsRepository
import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion
import ru.practicum.android.diploma.feature.filter.domain.model.FilterIndustry
import ru.practicum.android.diploma.feature.search.data.impl.PaginationRepositoryImpl
import ru.practicum.android.diploma.feature.search.data.impl.SearchRepositoryImpl
import ru.practicum.android.diploma.feature.search.domain.api.PaginationRepository
import ru.practicum.android.diploma.feature.search.domain.api.SearchRepository
import ru.practicum.android.diploma.feature.vacancy.data.impl.VacancyDetailMapper
import ru.practicum.android.diploma.feature.vacancy.data.impl.VacancyRepositoryImpl
import ru.practicum.android.diploma.feature.vacancy.domain.api.VacancyRepository

val repositoryModule = module {
    single<SearchRepository> {
        SearchRepositoryImpl(get(), get())
    }
    single<VacancyRepository> {
        VacancyRepositoryImpl(get(), get(), get())
    }

    single<FilterRegionsRepository> {
        FilterRegionsRepositoryImpl(get(), get())
    }

    single {
        VacancyDetailMapper()
    }

    single<FavoriteRepository> {
        FavoriteRepositoryImpl(get())
    }

    single<FiltersGettingRepository> {
        FiltersGettingRepositoryImpl(
            storageClient = get(),
            gson = get(),
            areaCountryType = object : TypeToken<AreaCountry>() {}.type,
            areaRegionType = object : TypeToken<AreaRegion>() {}.type,
            industryType = object : TypeToken<FilterIndustry>() {}.type
        )
    }

    single<FiltersSavingRepository> {
        FiltersSavingRepositoryImpl(
            storageClient = get(),
            gson = get(),
            areaCountryType = object : TypeToken<AreaCountry>() {}.type,
            areaRegionType = object : TypeToken<AreaRegion>() {}.type,
            industryType = object : TypeToken<FilterIndustry>() {}.type
        )
    }

    single<FiltersDeletingRepository> {
        FiltersDeletingRepositoryImpl(get())
    }

    single<SearchRepository> {
        SearchRepositoryImpl(get(), get())
    }

    single<IndustryRepository> {
        IndustryRepositoryImpl(get(), get())
    }

    single<FilterCountryRepository> {
        FilterCountryRepositoryImpl(get(), get())
    }

    factory<PaginationRepository> {
        PaginationRepositoryImpl(get())
    }
}
