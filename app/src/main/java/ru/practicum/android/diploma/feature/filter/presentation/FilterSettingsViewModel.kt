package ru.practicum.android.diploma.feature.filter.presentation

import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.feature.filter.domain.api.ClearFiltersUseCase
import ru.practicum.android.diploma.feature.filter.domain.api.DeleteFilterByKeyUseCase
import ru.practicum.android.diploma.feature.filter.domain.api.FilterSettingsInteractor

class FilterSettingsViewModel(
    private val filterSettingsInteractor: FilterSettingsInteractor,
    private val deleteFilterByKeyUseCase: DeleteFilterByKeyUseCase,
    private val clearFiltersUseCase: ClearFiltersUseCase
): ViewModel() {

}
