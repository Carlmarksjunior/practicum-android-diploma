package ru.practicum.android.diploma.feature.search.data.dto

import com.google.gson.annotations.SerializedName

class VacancySearchResponse(
    val found: Int,
    val pages: Int,
    val page: Int,
    @SerializedName("items")
    val vacancies: List<VacancyDto>
) : Response()
