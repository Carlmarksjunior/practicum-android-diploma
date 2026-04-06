package ru.practicum.android.diploma.feature.filter.domain.model

data class SearchFilters(
    val areaId: Int? = null,
    val industryId: Int? = null,
    val salary: Int? = null,
    val isOnlyWithSalary: Boolean = false,

)
