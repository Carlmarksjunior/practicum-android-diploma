package ru.practicum.android.diploma.feature.favorite.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.favorite.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.feature.favorite.domain.api.FavoriteRepository
import ru.practicum.android.diploma.feature.vacancy.domain.model.VacancyDetail

class FavoriteInteractorImpl(private val favoriteRepository: FavoriteRepository) : FavoriteInteractor {

    override suspend fun addToFavorites(vacancy: VacancyDetail) {
        favoriteRepository.addToFavorite(vacancy)
    }

    override suspend fun removeFromFavorites(vacancyId: String) {
        favoriteRepository.removeFavoriteById(vacancyId)
    }

    override suspend fun getFromFavoritesById(vacancyId: String): VacancyDetail {
        return favoriteRepository.getVacancyById(vacancyId)
    }

    override suspend fun isFavorite(vacancyId: String): Boolean {
        return favoriteRepository.isFavorite(vacancyId)
    }

    override fun getFavorites(offset: Int, limit: Int): Flow<List<VacancyDetail>> {
        return favoriteRepository.getFavorites(offset, limit)
    }
}
