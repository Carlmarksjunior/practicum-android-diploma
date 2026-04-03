package ru.practicum.android.diploma.feature.filter.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.vacancy.domain.model.Industry
import ru.practicum.android.diploma.util.Resource

interface IndustryRepository {
    fun searchIndustry(): Flow<Resource<List<Industry>>>
}
