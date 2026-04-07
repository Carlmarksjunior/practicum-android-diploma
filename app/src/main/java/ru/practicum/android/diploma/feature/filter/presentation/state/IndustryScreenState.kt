package ru.practicum.android.diploma.feature.filter.presentation.state

import ru.practicum.android.diploma.feature.filter.ui.IndustryUiModel

data class IndustryScreenState(
    val allIndustries: List<IndustryUiModel> = emptyList(),
    val visibleIndustries: List<IndustryUiModel> = emptyList(),
    val selectedIndustryId: String? = null,
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val errorMessageId: Int? = null
)
