package ru.practicum.android.diploma.feature.filter.data.impl.regions

import ru.practicum.android.diploma.feature.filter.data.dao.RegionDto
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion

class FilterRegionMapper {
    fun map(country: RegionDto): List<AreaRegion> {
        val result = mutableListOf<AreaRegion>()

        fun extractAreas(dto: RegionDto) {
            dto.areas?.forEach { area ->
                result.add(AreaRegion(
                    id = area.id ?: "",
                    name = area.name ?: "",
                    parentId = dto.id ?: "",
                    parentName = dto.name ?: ""
                ))
                extractAreas(area)
            }
        }

        extractAreas(country)
        return result
    }
}
