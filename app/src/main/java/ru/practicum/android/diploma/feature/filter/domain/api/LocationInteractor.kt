package ru.practicum.android.diploma.feature.filter.domain.api

import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion

interface LocationInteractor {
    fun getAreaCountry(): AreaCountry?
    fun getAreaRegion(): AreaRegion?
    fun saveAreaCountry(areaCountry: AreaCountry)
}
