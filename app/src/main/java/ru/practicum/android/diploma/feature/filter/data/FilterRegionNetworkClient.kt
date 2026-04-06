package ru.practicum.android.diploma.feature.filter.data

import ru.practicum.android.diploma.feature.filter.data.dao.RegionRequestDto
import ru.practicum.android.diploma.feature.search.data.dto.Response

interface FilterRegionNetworkClient {
    suspend fun doRequest(dto: RegionRequestDto): Response
}
