package ru.practicum.android.diploma.feature.filter.data.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.feature.filter.data.FilterRegionNetworkClient
import ru.practicum.android.diploma.feature.filter.data.dao.RegionRequestDto
import ru.practicum.android.diploma.feature.filter.data.dao.RegionResponse
import ru.practicum.android.diploma.feature.search.data.dto.Response
import ru.practicum.android.diploma.feature.search.data.network.VacancyApiService
import ru.practicum.android.diploma.util.ConnectionChecker
import java.io.IOException

class RetrofitFilterRegionNetworkClient(
    private val connectionChecker: ConnectionChecker,
    private val vacancyApiService: VacancyApiService
) : FilterRegionNetworkClient {

    override suspend fun doRequest(dto: RegionRequestDto): Response {
        if (!connectionChecker.isConnected()) {
            return Response().apply { code = NO_INTERNET_CODE }
        }

        return if (dto is RegionRequestDto) {
            withContext(Dispatchers.IO) {
                executeRequest(dto)
            }
        } else {
            Response().apply { code = BAD_REQUEST_CODE }
        }
    }

    private suspend fun executeRequest(dto: RegionRequestDto): Response {
        return try {
            val response = when (dto) {
                is RegionRequestDto.AllRegions -> vacancyApiService.getRegions()
            }
            RegionResponse().apply {
                regions = response
                code = SUCCESS_CODE
            }
        } catch (e: retrofit2.HttpException) {
            Log.e(TAG, "HTTP Error", e)
            Response().apply { code = e.code() }
        } catch (e: IOException) {
            Log.e(TAG, "Network Error", e)
            Response().apply { code = SERVER_ERROR_CODE }
        }
    }

    companion object {
        private const val TAG = "RetrofitNetworkClient"
        private const val NO_INTERNET_CODE = -1
        private const val BAD_REQUEST_CODE = 400
        private const val SUCCESS_CODE = 200
        private const val SERVER_ERROR_CODE = 500
    }
}
