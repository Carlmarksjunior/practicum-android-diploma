package ru.practicum.android.diploma.feature.vacancy.presentation

import ru.practicum.android.diploma.feature.vacancy.domain.model.VacancyDetail

sealed class VacancyState{
    object Loading : VacancyState()
    data class Content(val content: VacancyDetail) : VacancyState()
    data class Error(val errorMessage: String) : VacancyState()
}
