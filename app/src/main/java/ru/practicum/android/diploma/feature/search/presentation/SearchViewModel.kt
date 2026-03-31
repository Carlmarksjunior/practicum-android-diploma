package ru.practicum.android.diploma.feature.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.feature.search.domain.api.SearchInteractor
import ru.practicum.android.diploma.feature.search.domain.model.VacancyListInfo
import ru.practicum.android.diploma.feature.search.presentation.model.PagingErrorEvent
import ru.practicum.android.diploma.feature.search.presentation.model.SearchState
import ru.practicum.android.diploma.feature.vacancy.domain.model.VacancyDetail
import ru.practicum.android.diploma.util.Resource
import ru.practicum.android.diploma.util.SingleLiveEvent
import ru.practicum.android.diploma.util.debounce

class SearchViewModel(private val searchInteractor: SearchInteractor) : ViewModel() {
    private val searchState = MutableLiveData<SearchState>()
    fun observeSearchState(): LiveData<SearchState> = searchState
    private val errorPagingEvent = SingleLiveEvent<PagingErrorEvent>()
    fun observeErrorPagingEvent(): LiveData<PagingErrorEvent> = errorPagingEvent

    private var searchText = ""
    private var listVisible = false
    private var currentPage = 1
    private var lastRequestedPage = -1
    private var maxPage = -1
    private var itemPositionInvokingSearch = -1
    private val vacancies = mutableListOf<VacancyDetail>()
    private val searchDebounce = debounce<String>(DEBOUNCE_DELAY, viewModelScope, true) { query ->
        if (query.isNotEmpty() && query.isNotBlank()) {
            resetPage()
            firstPageRequest(query)
        }
    }

    fun onSearchTextChanged(text: String) {
        if (text == searchText) return
        searchText = text
        if (text.isEmpty()) {
            searchState.value = SearchState.Idle
            listVisible = false
        }
        if (text.isNotEmpty() && !listVisible) {
            searchState.value = SearchState.InputStarted
        }
        searchDebounce.invoke(text)
    }

    fun onListScroll(lastVisibleItemPosition: Int) {
        if (itemPositionInvokingSearch != -1 &&
            itemPositionInvokingSearch <= lastVisibleItemPosition && currentPage < maxPage) {
            searchState.value = SearchState.LoadingMore
            if (currentPage != lastRequestedPage) {
                lastRequestedPage = currentPage
                viewModelScope.launch {
                    doRequest(searchText).collect { vacancyList ->
                        when (vacancyList) {
                            is Resource.Error<VacancyListInfo> -> {
                                errorCodeResolve(vacancyList.message!!, {
                                    errorPagingEvent.value = PagingErrorEvent.NetworkError(R.string.no_internet)
                                }, {
                                    errorPagingEvent.value = PagingErrorEvent.RequestError(R.string.server_error)
                                })
                            }

                            is Resource.Success<VacancyListInfo> -> {
                                val data = vacancyList.data!!
                                onNextPage(data.pages, data.vacancies)
                                searchState.value = SearchState.Result(vacancies, data.found)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun firstPageRequest(query: String) {
        searchState.value = SearchState.Loading
        viewModelScope.launch {
            doRequest(query).collect { vacancyList ->
                when (vacancyList) {
                    is Resource.Success -> {
                        vacancies.clear()
                        val data = vacancyList.data!!
                        if (data.vacancies.isEmpty()) {
                            searchState.value = SearchState.EmptyResultError
                        } else {
                            onNextPage(data.pages, data.vacancies, 2)
                            searchState.value = SearchState.Result(vacancies, data.found)
                        }
                    }

                    is Resource.Error -> {
                        errorCodeResolve(vacancyList.message!!, {
                            searchState.value = SearchState.NetworkError(R.string.no_internet)
                        }, {
                            searchState.value = SearchState.RequestError(R.string.server_error)
                        })
                    }
                }
            }
        }
    }

    private fun errorCodeResolve(message: String, networkError: () -> Unit, requestError: () -> Unit) {
        val errorCode = message.toIntOrNull()
        errorCode?.apply {
            if (this == -1) {
                networkError()
            }
        } ?: {
            requestError()
        }
    }

    private fun doRequest(query: String): Flow<Resource<VacancyListInfo>> {
        return searchInteractor.searchVacancies(query, null, currentPage)
    }

    private fun onNextPage(newMaxPage: Int, newVacancies: List<VacancyDetail>, newCurrentPage: Int = currentPage + 1) {
        currentPage = newCurrentPage
        maxPage = newMaxPage
        vacancies.addAll(newVacancies)
        itemPositionInvokingSearch = vacancies.size - 1
        listVisible = true
    }

    private fun resetPage() {
        currentPage = 1
        itemPositionInvokingSearch = -1
        maxPage = -1
        lastRequestedPage = -1
    }

    companion object {
        private const val DEBOUNCE_DELAY = 2000L
    }
}
