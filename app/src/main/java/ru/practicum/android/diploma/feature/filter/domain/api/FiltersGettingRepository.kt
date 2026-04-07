package ru.practicum.android.diploma.feature.filter.domain.api

import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion
import ru.practicum.android.diploma.feature.filter.domain.model.FilterIndustry
import ru.practicum.android.diploma.feature.filter.domain.model.Filters

interface FiltersGettingRepository {
    fun getCountry(): AreaCountry?
    fun getRegion(): AreaRegion?
    fun getIndustry(): FilterIndustry?
    fun getSalary(): Int?
    fun getIsOnlyWithSalary(): Boolean?
    fun getAllFilters(): Filters?
}
