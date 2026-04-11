package ru.practicum.android.diploma.feature.search.domain.impl

import ru.practicum.android.diploma.feature.search.domain.api.PaginationInteractor
import ru.practicum.android.diploma.feature.search.domain.api.PaginationRepository

class PaginationInteractorImpl(private val paginationRepository: PaginationRepository) : PaginationInteractor {
    override fun setLastRequestedPage(lastRequestedPage: Int) {
        paginationRepository.setLastRequestedPage(lastRequestedPage)
    }

    override fun getLastRequestedPage(): Int {
        return paginationRepository.getLastRequestedPage()
    }

    override fun nextPage(): Int {
        return paginationRepository.nextPage()
    }

    override fun setMaxPages(maxPages: Int) {
        paginationRepository.setMaxPages(maxPages)
    }

    override fun reset() {
        paginationRepository.reset()
    }

    override fun nextPageAvailable(): Boolean {
        return paginationRepository.nextPageAvailable()
    }

    override fun getCurrentPage(): Int {
        return paginationRepository.getCurrentPage()
    }
}
