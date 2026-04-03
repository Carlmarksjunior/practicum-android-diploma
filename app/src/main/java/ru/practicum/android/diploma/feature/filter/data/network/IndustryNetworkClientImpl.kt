package ru.practicum.android.diploma.feature.filter.data.network

import android.util.Log
import okio.IOException
import ru.practicum.android.diploma.feature.filter.data.IndustryNetworkClient
import ru.practicum.android.diploma.feature.filter.data.dto.IndustryResponse
import ru.practicum.android.diploma.util.ConnectionChecker

class IndustryNetworkClientImpl(
    private val api: IndustryApiService,
    private val connectionChecker: ConnectionChecker
) : IndustryNetworkClient {

    override suspend fun getIndustries(): IndustryResponse {
        if (!connectionChecker.isConnected()) {
            return IndustryResponse(emptyList()).apply { code = NO_INTERNET_CODE }
        }

        return try {
            val list = api.getIndustriesRaw()
            IndustryResponse(list).apply { code = SUCCESS_CODE }
        } catch (e: IOException) {
            Log.e(TAG, "Network Error", e)
            IndustryResponse(emptyList()).apply { code = SERVER_ERROR_CODE }
        } catch (e: retrofit2.HttpException) {
            Log.e(TAG, "HTTP Error", e)
            IndustryResponse(emptyList()).apply { code = e.code() }
        }
    }

    companion object {
        private const val TAG = "IndustryNetworkClientImpl"
        private const val NO_INTERNET_CODE = -1
        private const val SUCCESS_CODE = 200
        private const val SERVER_ERROR_CODE = 500
    }

}
