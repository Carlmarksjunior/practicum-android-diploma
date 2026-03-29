package ru.practicum.android.diploma.feature.favorite.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.practicum.android.diploma.feature.favorite.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.feature.search.domain.model.Vacancy

class FavoriteFragmentViewModel(private val favoriteInteractor: FavoriteInteractor) : ViewModel() {

    private val favoriteLiveData = MutableLiveData<List<Vacancy>>()
    fun observeState(): LiveData<List<Vacancy>> = favoriteLiveData

    fun getFavorites(offset: Int) {
        viewModelScope.launch {
            favoriteInteractor.getFavorites(offset).collect { vacancies ->
                favoriteLiveData.postValue(vacancies)
            }
        }
    }

}
