package ru.practicum.android.diploma.feature.filter.domain.impl

import ru.practicum.android.diploma.feature.filter.domain.api.FiltersGettingRepository
import ru.practicum.android.diploma.feature.filter.domain.api.FilterSettingsInteractor
import ru.practicum.android.diploma.feature.filter.domain.model.Filters

class FilterSettingsInteractorImpl(private val repository: FiltersGettingRepository): FilterSettingsInteractor {
    override fun getAllFilters(): Filters? {
        return repository.getAllFilters()
    }
}
