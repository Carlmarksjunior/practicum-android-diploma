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

    fun init() {
        val filter = filterSettingsInteractor.getAllFilters()
        if (filter != null) {
            _filter.postValue(filter)
        }
    }

    fun setSalary(salary: Int) {
        filterSettingsInteractor.setSalary(salary)
    }

    fun setIsOnlyWithSalary(isOnlyWithSalary: Boolean) {
        filterSettingsInteractor.setIsOnlyWithSalary(isOnlyWithSalary)
    }

    fun deleteFilter(name: String) {
        deleteFilterByKeyUseCase.execute(name)
        init()
    }

}
