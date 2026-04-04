package ru.practicum.android.diploma.feature.search.domain.model

import ru.practicum.android.diploma.feature.vacancy.domain.model.Vacancy

data class VacancyListInfo(val found: Int, val pages: Int, val page: Int, val vacancies: List<Vacancy>)
