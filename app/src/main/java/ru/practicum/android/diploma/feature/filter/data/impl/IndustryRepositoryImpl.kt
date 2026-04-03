package ru.practicum.android.diploma.feature.filter.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.feature.filter.data.IndustryNetworkClient
import ru.practicum.android.diploma.feature.filter.data.dto.IndustryResponse
import ru.practicum.android.diploma.feature.filter.domain.api.IndustryRepository
import ru.practicum.android.diploma.feature.vacancy.domain.model.Industry
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.ResourceProvider

class IndustryRepositoryImpl(
    private val client: IndustryNetworkClient,
    private val resourceProvider: ResourceProvider
) : IndustryRepository {
    override fun searchIndustry(): Flow<Resource<List<Industry>>> = flow {
        val response = client.getIndustries()

        when (response.code) {
            SUCCESS_CODE -> {
                val industryResponse = response as IndustryResponse
                emit(Resource.Success(industryResponse.industries))
            }
            NO_INTERNET_CODE -> emit(Resource.Error(NO_INTERNET_CODE.toString(), listOf<Industry>()))

            else -> emit(Resource.Error(resourceProvider.getString(R.string.server_error), listOf<Industry>()))
        }

    }

    companion object {
        private const val NO_INTERNET_CODE = -1
        private const val SUCCESS_CODE = 200
    }

}
