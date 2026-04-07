package ru.practicum.android.diploma.feature.search.data.dto

sealed interface RequestDto {

    interface WithParams {
        val params: Map<String, String>
    }
    interface WithPathId {
        val id: String
    }

    data class FilteredVacancies(
        override val params: Map<String, String>
    ) : RequestDto, WithParams

    data class Vacancy(
        override val id: String
    ) : RequestDto, WithPathId

    object Area : RequestDto
}
