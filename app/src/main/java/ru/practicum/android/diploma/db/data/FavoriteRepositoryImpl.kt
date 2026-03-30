package ru.practicum.android.diploma.db.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.db.data.dao.VacancyDao
import ru.practicum.android.diploma.feature.favorite.domain.api.FavoriteRepository
import ru.practicum.android.diploma.feature.vacancy.domain.model.VacancyDetail
import ru.practicum.android.diploma.util.toDbEntity
import ru.practicum.android.diploma.util.toVacancyDetail

class FavoriteRepositoryImpl(private val vacancyDao: VacancyDao) : FavoriteRepository {
    override suspend fun addToFavorite(vacancy: VacancyDetail) {
        vacancyDao.insert(vacancy.toDbEntity())
    }

    override suspend fun removeFavoriteById(id: String) {
        vacancyDao.remove(id)
    }

    override fun getFavorites(offset: Int, limit: Int): Flow<List<VacancyDetail>> =
        vacancyDao.getAllByPage(offset, limit)
            .map { list -> list.map { it.toVacancyDetail() } }

    override suspend fun getVacancyById(id: String): VacancyDetail {
        return vacancyDao.getById(id).toVacancyDetail()
    }

    override suspend fun isFavorite(id: String): Boolean {
        return vacancyDao.isFavorite(id)
    }
}
