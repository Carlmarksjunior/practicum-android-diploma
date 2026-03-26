package ru.practicum.android.diploma.db.data.dao

import kotlinx.coroutines.flow.Flow

interface VacancyDao {
    suspend fun insert(vacancy: Any)
    suspend fun remove(id: String)
    fun getAllByPage(offset: Int): Flow<List<Any>>
    suspend fun getById(id: String): Any
}
