package ru.practicum.android.diploma.feature.filter.domain.api

interface FiltersDeletingRepository {
    fun deleteFilter(key: String)
    fun clearFilters()
}
