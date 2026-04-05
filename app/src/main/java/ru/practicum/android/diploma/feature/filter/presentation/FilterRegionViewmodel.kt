package ru.practicum.android.diploma.feature.filter.presentation

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
import ru.practicum.android.diploma.util.SingleLiveEvent

class FilterRegionViewmodel(private val filterRegionsInteractor: FilterRegionsInteractor) : ViewModel() {

    private val regionLiveData = MutableLiveData<FilterRegionState>()
    fun observeRegionLiveData(): LiveData<FilterRegionState> = regionLiveData

    private val saveLiveData = SingleLiveEvent<Boolean>()
    fun observeSaveLiveData(): LiveData<Boolean> = saveLiveData

    private var allRegions: List<AreaRegion>? = null

    fun onSearchTextChanged(text: String) {
        if (allRegions == null) {
            getRegions()
            return
        }

        if (text.isEmpty()) {
            regionLiveData.postValue(FilterRegionState.Content(allRegions ?: emptyList()))
        } else {
            getRegionsOnText(text)
        }
    }

    fun getRegions() {
        viewModelScope.launch(Dispatchers.IO) {
            regionLiveData.postValue(FilterRegionState.Loading)
            filterRegionsInteractor.getAllRegions().collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val regions = resource.data ?: emptyList()
                        allRegions = getRegionOnCountry(regions)

                        regionLiveData.postValue(FilterRegionState.Content(allRegions ?: emptyList()))
                    }

                    is Resource.Error -> {
                        regionLiveData.postValue(FilterRegionState.Error(resource.message ?: "Unknown Error"))
                    }
                }
            }
        }
    }

    private fun getRegionsOnText(text: String) {
        val filtered = allRegions?.filter { region ->
            region.name.contains(text.trim(), ignoreCase = true)
        } ?: emptyList()

        regionLiveData.postValue(FilterRegionState.Content(filtered))
    }

    private suspend fun getRegionOnCountry(allRegions: List<AreaRegion>): List<AreaRegion> {
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
            saveLiveData.postValue(true)
        }
    }
}
