package ru.practicum.android.diploma.feature.filter.domain.api

import ru.practicum.android.diploma.feature.vacancy.domain.model.Industry

interface IndustrySaveInteractor {
    fun setIndustry(industry: Industry)
}
