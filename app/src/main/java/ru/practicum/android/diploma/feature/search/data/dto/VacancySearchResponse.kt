package ru.practicum.android.diploma.feature.search.data.dto

class VacancySearchResponse(val found: Int, val pages: Int, val page: Int, val vacancies: List<VacancyDto>) : Response()
