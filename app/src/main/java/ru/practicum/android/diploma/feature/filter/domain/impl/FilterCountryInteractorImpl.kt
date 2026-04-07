package ru.practicum.android.diploma.feature.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.filter.domain.api.FilterCountryInteractor
import ru.practicum.android.diploma.feature.filter.domain.api.FilterCountryRepository
import ru.practicum.android.diploma.feature.filter.domain.api.FiltersSavingRepository
import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.util.Resource

class FilterCountryInteractorImpl(
    private val filterCountryRepository: FilterCountryRepository,
    private val filterSavingRepository: FiltersSavingRepository
) : FilterCountryInteractor {
    override fun getCountries(): Flow<Resource<List<AreaCountry>>> {
        return filterCountryRepository.getCountries()
    }

    override suspend fun saveCountryFilter(country: AreaCountry) {
        filterSavingRepository.setCountry(country)
    }
}
