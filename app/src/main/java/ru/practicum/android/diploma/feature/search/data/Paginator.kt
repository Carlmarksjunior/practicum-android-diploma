package ru.practicum.android.diploma.feature.search.data

interface Paginator {
    var lastRequestedPage: Int
    fun nextPage(): Int
    fun setMaxPages(maxPages: Int)
    fun reset()
    fun nextPageAvailable(): Boolean
    fun getCurrentPage(): Int
}
