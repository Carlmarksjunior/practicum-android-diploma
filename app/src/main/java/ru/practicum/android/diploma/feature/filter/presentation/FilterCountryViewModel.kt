package ru.practicum.android.diploma.feature.filter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.feature.filter.domain.api.FilterCountryInteractor
import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.util.Resource

class FilterCountryViewModel(
    private val interactor: FilterCountryInteractor
) : ViewModel() {

    private val filterCountryLiveData = MutableLiveData<FilterCountryState>()
    fun observeState(): LiveData<FilterCountryState> = filterCountryLiveData

    fun loadCountries() {
        viewModelScope.launch(Dispatchers.IO) {
            filterCountryLiveData.postValue(FilterCountryState.Loading)
            interactor.getCountries().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val countries = resource.data ?: emptyList()
                        renderState(FilterCountryState.Content(countries))
                    }

                    is Resource.Error -> {
                        renderState(FilterCountryState.Error(resource.message!!))
                    }
                }
            }
        }
    }

    fun saveCountryFilter(country: AreaCountry) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.saveCountryFilter(country)
        }
    }

    private fun renderState(state: FilterCountryState) {
        filterCountryLiveData.postValue(state)
    }
}
