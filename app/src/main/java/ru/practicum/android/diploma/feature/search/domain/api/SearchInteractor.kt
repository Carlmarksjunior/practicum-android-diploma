package ru.practicum.android.diploma.feature.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.filter.domain.model.SearchFilters
import ru.practicum.android.diploma.feature.search.domain.model.Vacancy

interface SearchInteractor {
    fun searchVacancies(
        expression: String,
        filter: SearchFilters?,
        pager: Int
    ): Flow<List<Vacancy>>
}
