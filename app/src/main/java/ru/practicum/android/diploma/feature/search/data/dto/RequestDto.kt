package ru.practicum.android.diploma.feature.search.data.dto

sealed interface RequestDto {

    interface WithParams : RequestDto {
        val params: Map<String, String>
    }
    interface WithPathId : RequestDto {
        val id: String
    }

    data class SearchWithParams(
        override val params: Map<String, String>
    ) : WithParams

    data class SearchWithPathId(
        override val id: String
    ) : WithPathId

}
