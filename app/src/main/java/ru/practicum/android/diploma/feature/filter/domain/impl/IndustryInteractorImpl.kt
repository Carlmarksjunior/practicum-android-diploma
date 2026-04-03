package ru.practicum.android.diploma.feature.filter.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.filter.domain.api.IndustryInteractor
import ru.practicum.android.diploma.feature.filter.domain.api.IndustryRepository
import ru.practicum.android.diploma.feature.vacancy.domain.model.Industry
import ru.practicum.android.diploma.util.Resource

class IndustryInteractorImpl(private val industryRepository: IndustryRepository) : IndustryInteractor {
    override fun searchIndustry(): Flow<Resource<List<Industry>>> {
        return industryRepository.searchIndustry()
    }
}
