package ru.practicum.android.diploma.feature.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.filter.domain.model.SearchFilters
import ru.practicum.android.diploma.feature.search.domain.model.Vacancy
import ru.practicum.android.diploma.util.Resource

interface SearchRepository {
    fun searchVacancies(
        expression: String,
        filter: SearchFilters?,
        pager: Int
    ): Flow<Resource<List<Vacancy>>>
}
