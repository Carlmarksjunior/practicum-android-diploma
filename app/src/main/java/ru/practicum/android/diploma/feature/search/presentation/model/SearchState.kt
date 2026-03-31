package ru.practicum.android.diploma.feature.search.presentation.model

import ru.practicum.android.diploma.feature.vacancy.domain.model.VacancyDetail

sealed interface SearchState {
    object Idle : SearchState
    object InputStarted : SearchState
    object Loading : SearchState
    data class Result(val vacancies: List<VacancyDetail>, val founded: Int) : SearchState
    data class NetworkError(val message: String) : SearchState
    object EmptyResultError : SearchState
    data class RequestError(val message: String) : SearchState
    object LoadingMore : SearchState
}
