package ru.practicum.android.diploma.feature.filter.domain.model

import ru.practicum.android.diploma.feature.vacancy.domain.model.Industry

data class Filters(
    val areaCountry: AreaCountry?,
    val areaRegion: AreaRegion?,
    val industry: Industry?,
    val salary: Int? = null,
    val isOnlyWithSalary: Boolean? = false,
)
