package ru.practicum.android.diploma.feature.search.presentation.model

sealed interface PagingErrorEvent {
    data class NetworkError(val message: Int) : PagingErrorEvent
    data class RequestError(val message: Int) : PagingErrorEvent
}
