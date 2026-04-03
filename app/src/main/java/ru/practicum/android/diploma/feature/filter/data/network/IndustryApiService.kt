package ru.practicum.android.diploma.feature.filter.data.network

import retrofit2.http.GET
import ru.practicum.android.diploma.feature.vacancy.domain.model.Industry

interface IndustryApiService {

    @GET("industries")
    suspend fun getIndustriesRaw(): List<Industry>
}
