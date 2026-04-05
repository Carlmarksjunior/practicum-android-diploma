package ru.practicum.android.diploma.feature.filter.presentation.states

import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion

sealed class FilterRegionState {
    object Loading : FilterRegionState()
    data class Content(val regions: List<AreaRegion>) : FilterRegionState()
    data class Error(val errorMessage: String) : FilterRegionState()
}
