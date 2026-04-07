package ru.practicum.android.diploma.feature.filter.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.feature.filter.data.dto.FilterCountryResponse
import ru.practicum.android.diploma.feature.filter.domain.api.FilterCountryRepository
import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.feature.search.data.NetworkClient
import ru.practicum.android.diploma.feature.search.data.dto.RequestDto
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResourceProvider

class FilterCountryRepositoryImpl(
    private val networkClient: NetworkClient,
    private val resourceProvider: ResourceProvider
) : FilterCountryRepository {
    override fun getCountries(): Flow<Resource<List<AreaCountry>>> = flow {
        val response = networkClient.doRequest(RequestDto.Area)
        when (response.code) {
            SUCCESS_CODE -> {
                with(response as FilterCountryResponse) {
                    val data = response.areas
                        .filter { it.id != null && it.name != null }
                        .map { AreaCountry(it.id!!, it.name!!) }
                    emit(Resource.Success(data))
                }
            }

            NO_INTERNET_CODE -> {
                emit(Resource.Error(resourceProvider.getString(R.string.no_internet)))
            }

            else -> {
                emit(Resource.Error(resourceProvider.getString(R.string.server_error)))
            }
        }
    }

    companion object {
        private const val NO_INTERNET_CODE = -1
        private const val SUCCESS_CODE = 200
    }
}
