package ru.practicum.android.diploma.feature.filter.domain.api.regions

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion
import ru.practicum.android.diploma.util.Resource

interface FilterRegionsRepository {
    fun getAllRegions(): Flow<Resource<List<AreaRegion>>>

}
