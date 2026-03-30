package ru.practicum.android.diploma.feature.favorite.domain

import ru.practicum.android.diploma.feature.vacancy.domain.model.VacancyDetail

sealed interface FavoritesState {

    data class Content(
        val vacancies: List<VacancyDetail>,
    ) : FavoritesState

    data class Error(
        val errorMessage: String
    ) : FavoritesState

    data class Empty(
        val message: String
    ) : FavoritesState
}
