package ru.practicum.android.diploma.feature.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.filter.domain.api.FiltersGettingRepository
import ru.practicum.android.diploma.feature.filter.domain.api.IndustryInteractor
import ru.practicum.android.diploma.feature.filter.domain.api.IndustryRepository
import ru.practicum.android.diploma.feature.filter.domain.model.FilterIndustry
import ru.practicum.android.diploma.feature.vacancy.domain.model.Industry
import ru.practicum.android.diploma.util.Resource

class IndustryInteractorImpl(
    private val industryRepository: IndustryRepository,
    private val filtersGettingRepositoryImpl: FiltersGettingRepository
) : IndustryInteractor {
    override fun searchIndustry(): Flow<Resource<List<Industry>>> {
        return industryRepository.searchIndustry()
    }

    override fun getIndustry(): FilterIndustry? {
        return filtersGettingRepositoryImpl.getIndustry()
    }
}
