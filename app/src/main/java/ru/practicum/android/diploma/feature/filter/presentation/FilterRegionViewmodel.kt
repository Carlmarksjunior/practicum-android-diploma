package ru.practicum.android.diploma.feature.filter.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.feature.filter.domain.api.regions.FilterRegionsInteractor
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion
import ru.practicum.android.diploma.feature.filter.presentation.states.FilterRegionState
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.debounce

class FilterRegionViewmodel(private val filterRegionsInteractor: FilterRegionsInteractor) : ViewModel() {

    private val regionLiveData = MutableLiveData<FilterRegionState>()
    fun observeRegionLiveData(): LiveData<FilterRegionState> = regionLiveData

    private val searchDebounce = debounce<String>(DEBOUNCE_DELAY, viewModelScope, true) { query ->
        if (query.isNotEmpty() && query.isNotBlank()) {
            getRegions(query)
        } else {
            getRegions()
        }
    }

    fun onSearchTextChanged(text: String) {
        searchDebounce.invoke(text)
        Log.d("FilterRegionViewmodel", "onSearchTextChanged: $text")
    }

    fun getRegions(regionName: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
//            filterRegionsInteractor.saveCountry(AreaCountry(5, "Украина"))
            regionLiveData.postValue(FilterRegionState.Loading)
            filterRegionsInteractor.getAllRegions().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val allRegions = getRegionOnCountry(resource.data ?: emptyList())
                        val filteredList = if (regionName.isNullOrBlank()) {
                            allRegions
                        } else {
                            allRegions.filter { region ->
                                region.name.contains(regionName.trim(), ignoreCase = true)
                            }
                        }
                        regionLiveData.postValue(FilterRegionState.Content(filteredList))
                    }

                    is Resource.Error -> {
                        regionLiveData.postValue(FilterRegionState.Error(resource.message!!))
                    }
                }
            }
        }
    }

    suspend fun getRegionOnCountry(allRegions: List<AreaRegion>): List<AreaRegion> {
        val selectedCountry = filterRegionsInteractor.getCountry()

        return if (selectedCountry != null) {
            allRegions.filter { region -> region.parentId == selectedCountry.id }
        } else {
            allRegions
        }
    }

    fun saveRegion(region: AreaRegion) {
        viewModelScope.launch(Dispatchers.IO) {
            filterRegionsInteractor.saveRegion(region)
        }
    }

    companion object {
        private const val DEBOUNCE_DELAY = 2000L
    }
}
