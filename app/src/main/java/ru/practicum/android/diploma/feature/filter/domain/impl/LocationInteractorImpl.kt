package ru.practicum.android.diploma.feature.filter.domain.impl

import ru.practicum.android.diploma.feature.filter.domain.api.FiltersGettingRepository
import ru.practicum.android.diploma.feature.filter.domain.api.FiltersSavingRepository
import ru.practicum.android.diploma.feature.filter.domain.api.LocationInteractor
import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion

class LocationInteractorImpl(
    private val gettingRepository: FiltersGettingRepository,
    private val savingRepository: FiltersSavingRepository
) : LocationInteractor {
    override fun getAreaCountry(): AreaCountry? {
        return gettingRepository.getCountry()
    }

    override fun getAreaRegion(): AreaRegion? {
        return gettingRepository.getRegion()
    }

    override fun saveAreaCountry(areaCountry: AreaCountry) {
        savingRepository.setCountry(areaCountry)
    }
}
