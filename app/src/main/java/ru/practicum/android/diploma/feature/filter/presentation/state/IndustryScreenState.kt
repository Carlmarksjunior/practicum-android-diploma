package ru.practicum.android.diploma.feature.filter.presentation.state

import ru.practicum.android.diploma.feature.filter.ui.IndustryUiModel

data class IndustryScreenState(
    val errorMessage: String? = null,
    val industries: List<IndustryUiModel> = listOf(),
    val selectedButtonView: Boolean = false
)

