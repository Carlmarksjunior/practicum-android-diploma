package ru.practicum.android.diploma.feature.filter.domain.api

import ru.practicum.android.diploma.feature.filter.domain.model.FilterIndustry

interface IndustrySaveInteractor {
    fun setIndustry(industry: FilterIndustry)
}
