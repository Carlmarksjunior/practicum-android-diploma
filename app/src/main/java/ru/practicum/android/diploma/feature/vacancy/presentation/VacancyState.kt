package ru.practicum.android.diploma.feature.vacancy.presentation

import ru.practicum.android.diploma.feature.vacancy.domain.model.Vacancy

sealed class VacancyState {
    object Loading : VacancyState()
    data class Content(val content: Vacancy, val isFavorite: Boolean) : VacancyState()
    data class Error(val errorMessage: String) : VacancyState()
}
