package ru.practicum.android.diploma.feature.filter.domain.model

data class SearchFilters(
    val areaId: String? = null,
    val industryId: String? = null,
    val salary: Int? = null,
    val isOnlyWithSalary: Boolean = false,

)
