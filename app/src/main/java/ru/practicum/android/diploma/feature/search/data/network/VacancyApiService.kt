package ru.practicum.android.diploma.feature.search.data.network

import retrofit2.http.GET
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.feature.search.data.dto.VacancySearchResponse

interface VacancyApiService {
    @GET("vacancies")
    suspend fun searchVacancies(
        @QueryMap params: Map<String, String>
    ): VacancySearchResponse

}
