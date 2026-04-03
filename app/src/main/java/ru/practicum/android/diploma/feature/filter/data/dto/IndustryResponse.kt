package ru.practicum.android.diploma.feature.filter.data.dto

import ru.practicum.android.diploma.feature.search.data.dto.Response
import ru.practicum.android.diploma.feature.vacancy.domain.model.Industry

class IndustryResponse(val industries: List<Industry>) : Response()
