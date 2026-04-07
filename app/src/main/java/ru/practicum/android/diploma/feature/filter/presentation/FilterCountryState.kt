package ru.practicum.android.diploma.feature.filter.presentation

import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry

sealed interface FilterCountryState {

    object Loading : FilterCountryState

    data class Content(
        val countries: List<AreaCountry>,
    ) : FilterCountryState

    data class Error(
        val errorMessage: String
    ) : FilterCountryState
}
