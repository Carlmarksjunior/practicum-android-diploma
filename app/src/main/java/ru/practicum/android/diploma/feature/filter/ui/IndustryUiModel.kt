package ru.practicum.android.diploma.feature.filter.ui

import ru.practicum.android.diploma.feature.vacancy.domain.model.Industry

data class IndustryUiModel(
    val id: String?,
    val name: String?,
    val isSelected: Boolean
)

fun Industry.toUiModel() = IndustryUiModel(
    id = id,
    name = name,
    isSelected = false
)
