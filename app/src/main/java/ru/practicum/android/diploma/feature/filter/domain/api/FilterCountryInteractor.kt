package ru.practicum.android.diploma.feature.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.util.Resource

interface FilterCountryInteractor {

    fun getCountries(): Flow<Resource<List<AreaCountry>>>

    suspend fun saveCountryFilter(country: AreaCountry)

}
