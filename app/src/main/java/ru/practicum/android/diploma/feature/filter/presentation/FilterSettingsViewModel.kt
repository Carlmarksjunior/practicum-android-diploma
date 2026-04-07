package ru.practicum.android.diploma.feature.filter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.feature.filter.domain.api.ClearFiltersUseCase
import ru.practicum.android.diploma.feature.filter.domain.api.DeleteFilterByKeyUseCase
import ru.practicum.android.diploma.feature.filter.domain.api.FilterSettingsInteractor
import ru.practicum.android.diploma.feature.filter.domain.model.Filters

class FilterSettingsViewModel(
    private val filterSettingsInteractor: FilterSettingsInteractor,
    private val deleteFilterByKeyUseCase: DeleteFilterByKeyUseCase,
    private val clearFiltersUseCase: ClearFiltersUseCase
) : ViewModel() {

    private val _filter = MutableLiveData<Filters>()
    val filter: LiveData<Filters> = _filter

    private val stateSettingsScreen = MutableLiveData<Boolean>()
    fun observeStateSettingScreen(): LiveData<Boolean> = stateSettingsScreen

    fun init() {
        val filter = filterSettingsInteractor.getAllFilters()
        val currentFilter = filter ?: Filters(null, null, null, null, false)
        _filter.value = currentFilter

        val hasActiveFilters = currentFilter.areaCountry != null ||
            currentFilter.areaRegion != null ||
            currentFilter.industry != null ||
            currentFilter.salary != null ||
            currentFilter.isOnlyWithSalary == true

        stateSettingsScreen.value = hasActiveFilters
    }

    fun setSalary(salary: Int) {
        filterSettingsInteractor.setSalary(salary)
        stateSettingsScreen.postValue(true)
    }

    fun setIsOnlyWithSalary(isOnlyWithSalary: Boolean) {
        filterSettingsInteractor.setIsOnlyWithSalary(isOnlyWithSalary)
        init()
    }

    fun deleteFilter(name: String) {
        deleteFilterByKeyUseCase.execute(name)
        init()
    }

    fun clearFilters() {
        clearFiltersUseCase.execute()
        init()
    }

}
