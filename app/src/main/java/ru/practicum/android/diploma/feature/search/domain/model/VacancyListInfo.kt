package ru.practicum.android.diploma.feature.search.domain.model

data class VacancyListInfo(val found: Int, val pages: Int, val vacancies: List<Vacancy>)
