package ru.practicum.android.diploma.feature.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.filter.domain.api.FiltersGettingRepository
import ru.practicum.android.diploma.feature.filter.domain.api.FiltersSavingRepository
import ru.practicum.android.diploma.feature.filter.domain.api.regions.FilterRegionsInteractor
import ru.practicum.android.diploma.feature.filter.domain.api.regions.FilterRegionsRepository
import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion
import ru.practicum.android.diploma.util.Resource

class FilterRegionInteractorImpl(
    private val filterRegionsRepository: FilterRegionsRepository,
    private val filtersSavingRepository: FiltersSavingRepository,
    private val filtersGettingRepository: FiltersGettingRepository
) : FilterRegionsInteractor {

    override fun getAllRegions(): Flow<Resource<List<AreaRegion>>> {
        return filterRegionsRepository.getAllRegions()
    }

    override suspend fun saveRegion(region: AreaRegion) {
        filtersSavingRepository.setRegion(region)
    }

    override suspend fun getCountry(): AreaCountry? {
        return filtersGettingRepository.getCountry()
    }
}
