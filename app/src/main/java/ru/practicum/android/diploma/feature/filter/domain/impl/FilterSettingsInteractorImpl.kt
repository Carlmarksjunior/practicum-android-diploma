package ru.practicum.android.diploma.feature.filter.domain.impl

import ru.practicum.android.diploma.feature.filter.domain.api.FilterSettingsInteractor
import ru.practicum.android.diploma.feature.filter.domain.api.FiltersGettingRepository
import ru.practicum.android.diploma.feature.filter.domain.api.FiltersSavingRepository
import ru.practicum.android.diploma.feature.filter.domain.model.Filters
class FilterSettingsInteractorImpl(
    private val repository: FiltersGettingRepository,
    private val filtersSavingRepository: FiltersSavingRepository
) : FilterSettingsInteractor {
    override fun getAllFilters(): Filters? {
        return repository.getAllFilters()
    }

    override fun setSalary(salary: Int) {
        filtersSavingRepository.setSalary(salary)
    }

    override fun setIsOnlyWithSalary(isOnlyWithSalary: Boolean) {
        filtersSavingRepository.setIsOnlyWithSalary(isOnlyWithSalary)
    }
}
