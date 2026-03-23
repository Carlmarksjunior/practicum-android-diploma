package ru.practicum.android.diploma.feature.search.data

import ru.practicum.android.diploma.feature.search.data.dto.RequestDto
import ru.practicum.android.diploma.feature.search.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: RequestDto): Response
}
