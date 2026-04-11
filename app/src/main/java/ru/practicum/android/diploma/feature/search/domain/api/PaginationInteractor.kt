package ru.practicum.android.diploma.feature.search.domain.api

interface PaginationInteractor {
    fun setLastRequestedPage(lastRequestedPage: Int)
    fun getLastRequestedPage(): Int
    fun nextPage(): Int
    fun setMaxPages(maxPages: Int)
    fun reset()
    fun nextPageAvailable(): Boolean
    fun getCurrentPage(): Int
}
