package ru.practicum.android.diploma.feature.search.domain.model

import ru.practicum.android.diploma.feature.vacancy.domain.model.VacancyDetail

data class VacancyListInfo(val found: Int, val pages: Int, val vacancies: List<VacancyDetail>)
