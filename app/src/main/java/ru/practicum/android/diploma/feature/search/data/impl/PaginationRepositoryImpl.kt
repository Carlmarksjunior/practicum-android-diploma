package ru.practicum.android.diploma.feature.search.data.impl

import ru.practicum.android.diploma.feature.search.data.Paginator
import ru.practicum.android.diploma.feature.search.domain.api.PaginationRepository

class PaginationRepositoryImpl(private val paginator: Paginator) : PaginationRepository {
    override fun setLastRequestedPage(lastRequestedPage: Int) {
        paginator.lastRequestedPage = lastRequestedPage
    }

    override fun getLastRequestedPage(): Int {
        return paginator.lastRequestedPage
    }

    override fun nextPage(): Int {
        return paginator.nextPage()
    }

    override fun setMaxPages(maxPages: Int) {
        paginator.setMaxPages(maxPages)
    }

    override fun reset() {
        paginator.reset()
    }

    override fun nextPageAvailable(): Boolean {
        return paginator.nextPageAvailable()
    }

    override fun getCurrentPage(): Int {
        return paginator.getCurrentPage()
    }
}
