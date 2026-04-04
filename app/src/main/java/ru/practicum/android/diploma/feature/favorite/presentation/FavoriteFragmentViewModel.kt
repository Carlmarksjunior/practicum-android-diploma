package ru.practicum.android.diploma.feature.favorite.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.feature.favorite.domain.FavoritesState
import ru.practicum.android.diploma.feature.favorite.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.feature.vacancy.domain.model.Vacancy
import ru.practicum.android.diploma.util.ResourceProvider
import ru.practicum.android.diploma.util.SingleLiveEvent

class FavoriteFragmentViewModel(
    private val favoriteInteractor: FavoriteInteractor,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val favoriteLiveData = MutableLiveData<FavoritesState>()
    fun observeState(): LiveData<FavoritesState> = favoriteLiveData

    private val toastMessage = SingleLiveEvent<String>()
    fun observeToastMessages(): LiveData<String> = toastMessage

    private var currentPage = 0
    private val pageSize = PAGE_SIZE
    private var hasMore = true
    private var isLoading = false

    private val _vacancies = mutableListOf<Vacancy>()

    fun loadFirstPage() {
        currentPage = 0
        hasMore = true
        isLoading = false
        _vacancies.clear()
        loadNextPage()
    }

    fun loadNextPage() {
        if (isLoading || !hasMore) return

        isLoading = true

        viewModelScope.launch {
            @Suppress("TooGenericExceptionCaught")
            try {
                val offset = currentPage * pageSize
                val newVacancies = favoriteInteractor.getFavorites(offset, pageSize).first()

                if (currentPage == 0 && newVacancies.isEmpty()) {
                    renderState(FavoritesState.Empty(resourceProvider.getString(R.string.empty_list)))
                    hasMore = false
                    @Suppress("LabeledExpression")
                    return@launch
                }

                _vacancies.addAll(newVacancies)
                hasMore = newVacancies.size == pageSize
                renderState(FavoritesState.Content(_vacancies.toList()))
                currentPage++
            } catch (e: Exception) {
                if (_vacancies.isEmpty()) {
                    renderState(
                        FavoritesState.Error(e.message ?: resourceProvider.getString(R.string.unsuccessful_query))
                    )
                } else {
                    toastMessage.postValue(resourceProvider.getString(R.string.unsuccessful_query))
                }
            } finally {
                isLoading = false
            }
        }
    }

    private fun renderState(state: FavoritesState) {
        favoriteLiveData.postValue(state)
    }

    companion object {
        const val PAGE_SIZE = 20
    }
}
