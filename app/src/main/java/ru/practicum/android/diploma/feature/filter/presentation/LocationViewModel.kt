package ru.practicum.android.diploma.feature.filter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.feature.filter.domain.api.DeleteFilterByKeyUseCase
import ru.practicum.android.diploma.feature.filter.domain.api.LocationInteractor
import ru.practicum.android.diploma.feature.filter.domain.impl.DeleteFilterByKeyUseCaseImpl
import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion
import ru.practicum.android.diploma.feature.filter.presentation.state.FilterLocationState

class LocationViewModel(
    private val locationInteractor: LocationInteractor,
    private val deleteFilterByKeyUseCase: DeleteFilterByKeyUseCase
) : ViewModel() {
    private val locationState = MutableLiveData<FilterLocationState>()
    fun observeLocationState(): LiveData<FilterLocationState> = locationState

    private var areaCountry: AreaCountry? = null
    private var areaRegion: AreaRegion? = null

    fun getFilters() {
        areaCountry = locationInteractor.getAreaCountry()
        areaRegion = locationInteractor.getAreaRegion()
        if (areaRegion == null && areaCountry == null) {
            locationState.value = FilterLocationState.Empty
        } else {
            if (areaCountry == null && areaRegion != null) {
                areaCountry = AreaCountry(areaRegion!!.parentId, areaRegion!!.parentName)
                locationInteractor.saveAreaCountry(areaCountry!!)
            }
            locationState.value = FilterLocationState.Success(areaCountry?.name ?: "",
                areaRegion?.name ?: "")
        }
    }

    fun removeCountryFilter() {
        deleteFilterByKeyUseCase.execute(DeleteFilterByKeyUseCaseImpl.AREA_COUNTRY_KEY)
        areaCountry = null
        if (areaRegion == null) {
            locationState.value = FilterLocationState.Empty
        } else {
            locationState.value = FilterLocationState.Success("", areaRegion!!.name)
        }
    }

    fun removeRegionFilter() {
        deleteFilterByKeyUseCase.execute(DeleteFilterByKeyUseCaseImpl.AREA_REGION_KEY)
        areaRegion = null
        if (areaCountry == null) {
            locationState.value = FilterLocationState.Empty
        } else {
            locationState.value = FilterLocationState.Success(areaCountry!!.name, "")
        }
    }
}
