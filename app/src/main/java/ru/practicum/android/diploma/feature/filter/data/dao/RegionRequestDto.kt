package ru.practicum.android.diploma.feature.filter.data.dao

sealed interface RegionRequestDto {

    interface WithoutName
    class AllRegions : RegionRequestDto, WithoutName
}
