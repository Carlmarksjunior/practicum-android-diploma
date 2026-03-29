package ru.practicum.android.diploma.feature.favorite.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.favorite.domain.api.FavoriteInteractor
import ru.practicum.android.diploma.feature.search.domain.model.Vacancy

class FavoriteInteractorImpl: FavoriteInteractor {

    override fun addToFavorites(vacancy: Vacancy) {
        TODO("Not yet implemented")
    }

    override fun removeFromFavorites(vacancyId: String) {
        TODO("Not yet implemented")
    }

    override fun getFromFavoritesById(vacancyId: String): Vacancy? {
        TODO("Not yet implemented")
    }

    override fun isFavorite(vacancyId: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun getFavorites(offset: Int): Flow<List<Vacancy>> {
        TODO("Not yet implemented")
    }
}
