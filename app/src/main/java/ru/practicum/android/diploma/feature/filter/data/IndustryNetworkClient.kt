package ru.practicum.android.diploma.feature.filter.data

import ru.practicum.android.diploma.feature.filter.data.dto.IndustryResponse

interface IndustryNetworkClient {
    suspend fun getIndustries(): IndustryResponse
}
