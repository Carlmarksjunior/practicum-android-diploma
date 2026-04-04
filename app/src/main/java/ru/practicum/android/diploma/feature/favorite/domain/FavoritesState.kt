package ru.practicum.android.diploma.feature.favorite.domain

import ru.practicum.android.diploma.feature.vacancy.domain.model.Vacancy

sealed interface FavoritesState {

    data class Content(
        val vacancies: List<Vacancy>,
    ) : FavoritesState

    data class Error(
        val errorMessage: String
    ) : FavoritesState

    data class Empty(
        val message: String
    ) : FavoritesState
}
