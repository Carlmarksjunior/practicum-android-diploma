package ru.practicum.android.diploma.feature.filter.data.impl.regions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.feature.filter.data.FilterRegionNetworkClient
import ru.practicum.android.diploma.feature.filter.data.dao.RegionRequestDto
import ru.practicum.android.diploma.feature.filter.data.dao.RegionResponse
import ru.practicum.android.diploma.feature.filter.domain.api.regions.FilterRegionsRepository
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResourceProvider

class FilterRegionsRepositoryImpl(
    private val filterRegionNetworkClient: FilterRegionNetworkClient,
    private val resourceProvider: ResourceProvider
) : FilterRegionsRepository {
    override fun getAllRegions(): Flow<Resource<List<AreaRegion>>> = flow {
        val response = filterRegionNetworkClient.doRequest(RegionRequestDto.AllRegions())
        when (response.code) {
            SUCCESS_CODE -> {
                val result =
                    (response as RegionResponse).regions?.flatMap { FilterRegionMapper().map(it) } ?: emptyList()
                emit(Resource.Success(result))
            }

            NO_INTERNET_CODE -> emit(Resource.Error(NO_INTERNET_CODE.toString()))
            else -> emit(Resource.Error(resourceProvider.getString(R.string.server_error)))
        }
    }

    companion object {
        private const val NO_INTERNET_CODE = -1
        private const val SUCCESS_CODE = 200
    }
}
