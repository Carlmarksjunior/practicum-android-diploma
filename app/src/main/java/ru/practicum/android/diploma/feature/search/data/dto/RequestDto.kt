package ru.practicum.android.diploma.feature.search.data.dto

sealed interface RequestDto {
    data class WithParams(val params: Map<String, String>) : RequestDto
    data class WithPathId(val id: String) : RequestDto
}
