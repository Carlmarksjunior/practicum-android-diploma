package ru.practicum.android.diploma.feature.filter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.feature.filter.domain.api.IndustryInteractor
import ru.practicum.android.diploma.feature.filter.presentation.state.IndustryScreenState
import ru.practicum.android.diploma.feature.filter.ui.IndustryUiModel
import ru.practicum.android.diploma.feature.filter.ui.toUiModel
import ru.practicum.android.diploma.feature.vacancy.domain.model.Industry

class IndustryViewModel(
    private val industryInteractor: IndustryInteractor
) : ViewModel() {

    private val _industryScreenState = MutableLiveData<IndustryScreenState>()
    val industryScreenState: LiveData<IndustryScreenState> = _industryScreenState

    private var searchIndustryResult: List<IndustryUiModel> = arrayListOf()
    private var isSelected: String = ""

    fun init() {
        viewModelScope.launch {
            industryInteractor.searchIndustry().collect { result ->
                val uiModels = result.data?.map(Industry::toUiModel) ?: emptyList()
                searchIndustryResult = uiModels
                _industryScreenState.postValue(
                    IndustryScreenState(
                        errorMessage = result.message,
                        industries = uiModels,
                        selectedButtonView = false
                    )
                )
            }
        }
    }

    fun saveSelectIndustry() {

    }

    fun selectIndustry(id: String, text: String) {
        isSelected = id
        if (text.isNotEmpty()) {
            onSearchTextChanged(text)
            return
        }
        val searchIndustryResult = searchIndustryResult.map { industry ->
            industry.copy(isSelected = industry.id == id)
        }
        _industryScreenState.postValue(
            IndustryScreenState(
                errorMessage = null,
                industries = searchIndustryResult,
                selectedButtonView = true
            )
        )
    }

    fun onSearchTextChanged(text: String) {
        if (text.isEmpty()) {
            selectIndustry(isSelected, text)
            return
        }
        var filtered = searchIndustryResult.filter { industry ->
            industry.name?.contains(text, ignoreCase = true) ?: false
        }
        filtered = filtered.map { industry ->
            industry.copy(isSelected = industry.id == isSelected)
        }
        _industryScreenState.postValue(
            IndustryScreenState(
                errorMessage = null,
                industries = filtered,
                selectedButtonView = true
            )
        )
    }

}
