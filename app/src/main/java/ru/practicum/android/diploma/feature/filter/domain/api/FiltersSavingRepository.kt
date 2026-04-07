package ru.practicum.android.diploma.feature.filter.domain.api

import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion
import ru.practicum.android.diploma.feature.filter.domain.model.FilterIndustry

interface FiltersSavingRepository {
    fun setCountry(country: AreaCountry)
    fun setRegion(region: AreaRegion)
    fun setIndustry(industry: FilterIndustry)
    fun setSalary(salary: Int)
    fun setIsOnlyWithSalary(isOnlyWithSalary: Boolean)
}
