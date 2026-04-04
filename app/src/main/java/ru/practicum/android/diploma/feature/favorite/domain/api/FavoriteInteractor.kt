package ru.practicum.android.diploma.feature.favorite.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.vacancy.domain.model.Vacancy

interface FavoriteInteractor {
    suspend fun addToFavorites(vacancy: Vacancy)

    suspend fun removeFromFavorites(vacancyId: String)

    suspend fun getFromFavoritesById(vacancyId: String): Vacancy?

    suspend fun isFavorite(vacancyId: String): Boolean

    fun getFavorites(offset: Int, limit: Int): Flow<List<Vacancy>>
}
