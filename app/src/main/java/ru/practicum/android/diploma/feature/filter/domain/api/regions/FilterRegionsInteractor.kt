package ru.practicum.android.diploma.feature.filter.domain.api.regions

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion
import ru.practicum.android.diploma.util.Resource

interface FilterRegionsInteractor {
    fun getAllRegions(): Flow<Resource<List<AreaRegion>>>
    suspend fun saveRegion(region: AreaRegion)

    suspend fun getCountry(): AreaCountry?
}
