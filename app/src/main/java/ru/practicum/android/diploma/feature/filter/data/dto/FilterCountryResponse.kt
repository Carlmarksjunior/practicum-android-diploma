package ru.practicum.android.diploma.feature.filter.data.dto

import ru.practicum.android.diploma.feature.search.data.dto.AreaDto
import ru.practicum.android.diploma.feature.search.data.dto.Response

class FilterCountryResponse(
    val areas: List<AreaDto>
) : Response()
