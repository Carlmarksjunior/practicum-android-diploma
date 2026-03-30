package ru.practicum.android.diploma.feature.favorite.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.vacancy.domain.model.VacancyDetail

interface FavoriteRepository {
    suspend fun addToFavorite(vacancy: VacancyDetail)

    suspend fun removeFavoriteById(id: String)

    fun getFavorites(offset: Int, limit: Int): Flow<List<VacancyDetail>>

    suspend fun getVacancyById(id: String): VacancyDetail

    suspend fun isFavorite(id: String): Boolean
}
