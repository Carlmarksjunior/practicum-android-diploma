package ru.practicum.android.diploma.feature.filter.domain.api

import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion
import ru.practicum.android.diploma.feature.vacancy.domain.model.Industry

interface FiltersSavingRepository {
    fun setCountry(country: AreaCountry)
    fun setRegion(region: AreaRegion)
    fun setIndustry(industry: Industry)
    fun setSalary(salary: Int)
    fun setIsOnlyWithSalary(isOnlyWithSalary: Boolean)
}
