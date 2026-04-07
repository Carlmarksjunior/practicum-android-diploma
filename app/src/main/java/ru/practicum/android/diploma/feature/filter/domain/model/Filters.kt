package ru.practicum.android.diploma.feature.filter.domain.model

data class Filters(
    val areaCountry: AreaCountry?,
    val areaRegion: AreaRegion?,
    val industry: FilterIndustry?,
    val salary: Int? = null,
    val isOnlyWithSalary: Boolean? = false,
)
