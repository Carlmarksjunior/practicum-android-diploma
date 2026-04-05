package ru.practicum.android.diploma.feature.filter.presentation.state

sealed interface FilterLocationState {
    object Empty : FilterLocationState
    data class Success(val country: String, val region: String) : FilterLocationState
}
