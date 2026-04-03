package ru.practicum.android.diploma.feature.filter.domain.impl

import ru.practicum.android.diploma.feature.filter.domain.api.FiltersSavingRepository
import ru.practicum.android.diploma.feature.filter.domain.api.IndustrySaveInteractor
import ru.practicum.android.diploma.feature.vacancy.domain.model.Industry

class IndustrySaveInteractorImpl(
    private val repository: FiltersSavingRepository
) : IndustrySaveInteractor {
    override fun setIndustry(industry: Industry) {
        repository.setIndustry(industry)
    }
}
