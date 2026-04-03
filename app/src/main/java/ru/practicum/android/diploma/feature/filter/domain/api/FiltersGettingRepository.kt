package ru.practicum.android.diploma.feature.filter.domain.api

import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion
import ru.practicum.android.diploma.feature.filter.domain.model.Filters
import ru.practicum.android.diploma.feature.filter.domain.model.Industry

interface FiltersGettingRepository {
    fun getCountry(): AreaCountry?
    fun getRegion(): AreaRegion?
    fun getIndustry(): Industry?
    fun getSalary(): Int?
    fun getIsOnlyWithSalary(): Boolean?
    fun getAllFilters(): Filters?
}
