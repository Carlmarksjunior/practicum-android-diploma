package ru.practicum.android.diploma.feature.search.data.pagination

import ru.practicum.android.diploma.feature.search.data.Paginator

class Pagination(private val pageStartIndex: Int) : Paginator {
    private var maxPages: Int = -1
    private var currentPage: Int = -1
    override var lastRequestedPage: Int = -1

    init {
        currentPage = pageStartIndex
    }

    override fun nextPage(): Int {
        if (currentPage < maxPages) {
            currentPage++
        }
        return currentPage
    }

    override fun setMaxPages(maxPages: Int) {
        this.maxPages = maxPages
    }

    override fun reset() {
        currentPage = pageStartIndex
        maxPages = -1
        lastRequestedPage = -1
    }

    override fun nextPageAvailable(): Boolean {
        val available = currentPage <= maxPages && currentPage != lastRequestedPage
        if (available) {
            lastRequestedPage = currentPage
        }
        return available
    }

    override fun getCurrentPage(): Int {
        return currentPage
    }
}
