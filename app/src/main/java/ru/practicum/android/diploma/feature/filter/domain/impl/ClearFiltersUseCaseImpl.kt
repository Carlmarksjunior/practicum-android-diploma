package ru.practicum.android.diploma.feature.filter.domain.impl

import ru.practicum.android.diploma.feature.filter.domain.api.ClearFiltersUseCase
import ru.practicum.android.diploma.feature.filter.domain.api.FiltersDeletingRepository

class ClearFiltersUseCaseImpl(private val repository: FiltersDeletingRepository) : ClearFiltersUseCase {
    override fun execute() {
        repository.clearFilters()
    }
}
