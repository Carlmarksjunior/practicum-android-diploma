package ru.practicum.android.diploma.feature.search.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.filter.domain.api.FiltersGettingRepository
import ru.practicum.android.diploma.feature.filter.domain.model.SearchFilters
import ru.practicum.android.diploma.feature.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.feature.search.domain.api.SearchRepository
import ru.practicum.android.diploma.feature.search.domain.model.VacancyListInfo
import ru.practicum.android.diploma.util.Resource

class SearchInteractorImpl(
    private val repository: SearchRepository,
    private val filtersGettingRepository: FiltersGettingRepository
) : SearchInteractor {
    override fun searchVacancies(
        expression: String,
        filter: SearchFilters?,
        pager: Int
    ): Flow<Resource<VacancyListInfo>> {
        return repository.searchVacancies(expression, filter, pager)
    }

    override suspend fun getAllFilters(): SearchFilters {
        return SearchFilters(
            areaId = filtersGettingRepository.getRegion()?.id?.toInt()
                ?: filtersGettingRepository.getCountry()?.id?.toInt(),
            industryId = filtersGettingRepository.getIndustry()?.id?.toInt(),
            salary = filtersGettingRepository.getSalary(),
            isOnlyWithSalary = filtersGettingRepository.getIsOnlyWithSalary() ?: false
        )
    }
}
