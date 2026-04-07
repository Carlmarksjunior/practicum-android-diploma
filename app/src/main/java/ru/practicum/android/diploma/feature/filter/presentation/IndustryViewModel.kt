package ru.practicum.android.diploma.feature.filter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.feature.filter.domain.api.IndustryInteractor
import ru.practicum.android.diploma.feature.filter.domain.api.IndustrySaveInteractor
import ru.practicum.android.diploma.feature.filter.domain.model.FilterIndustry
import ru.practicum.android.diploma.feature.filter.presentation.state.IndustryScreenState
import ru.practicum.android.diploma.feature.filter.ui.toUiModel
import ru.practicum.android.diploma.feature.vacancy.domain.model.Industry

class IndustryViewModel(
    private val industryInteractor: IndustryInteractor,
    private val saveIndustryInteractor: IndustrySaveInteractor
) : ViewModel() {

    private val _industryScreenState = MutableLiveData<IndustryScreenState>()
    val industryScreenState: LiveData<IndustryScreenState> = _industryScreenState

    private fun updateState(newState: IndustryScreenState) {
        _industryScreenState.value = newState
    }

    fun init() {
        viewModelScope.launch {
            industryInteractor.searchIndustry().collect { result ->
                val uiModels = result.data?.map(Industry::toUiModel).orEmpty()

                val currentState = _industryScreenState.value ?: IndustryScreenState()
                val newState = currentState.copy(
                    allIndustries = uiModels,
                    visibleIndustries = uiModels,
                    isLoading = false,
                    errorMessage = result.message
                )

                updateState(newState)
            }
        }
    }

    fun selectIndustry(id: String) {
        val currentState = _industryScreenState.value ?: IndustryScreenState()

        val updatedIndustries = currentState.visibleIndustries.map {
            it.copy(isSelected = it.id == id)
        }
        val newState = currentState.copy(
            visibleIndustries = updatedIndustries,
            selectedIndustryId = id
        )

        updateState(newState)
    }

    fun onSearchTextChanged(text: String) {
        val currentState = _industryScreenState.value ?: IndustryScreenState()

        val updatedIndustries = if (text.isEmpty()) {
            currentState.allIndustries.map {
                it.copy(isSelected = it.id == currentState.selectedIndustryId)
            }
        } else {
            currentState.allIndustries
                .map { it.copy(isSelected = it.id == currentState.selectedIndustryId) }
                .filter { it.name?.contains(text, ignoreCase = true) == true }
        }

        val newState = currentState.copy(
            searchQuery = text,
            visibleIndustries = updatedIndustries
        )

        updateState(newState)
    }

    fun saveIndustry() {
        val currentState = _industryScreenState.value ?: IndustryScreenState()

        val target = currentState.allIndustries.find { it.id == currentState.selectedIndustryId }
        if (target != null && target.id != null && target.name != null) {
            saveIndustryInteractor.setIndustry(FilterIndustry(target.id, target.name))
        }
    }

}
