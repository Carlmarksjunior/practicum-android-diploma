package ru.practicum.android.diploma.feature.favorite.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.search.domain.model.Vacancy

interface FavoriteInteractor {
    fun addToFavorites(vacancy: Vacancy)
    fun removeFromFavorites(vacancyId: String)
    fun getFromFavoritesById(vacancyId: String): Vacancy?
    fun isFavorite(vacancyId: String): Boolean
    fun getFavorites(offset: Int): Flow<List<Vacancy>>
}
