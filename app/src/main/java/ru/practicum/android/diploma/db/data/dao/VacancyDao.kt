package ru.practicum.android.diploma.db.data.dao

interface VacancyDao {
    suspend fun insert(vacancy: Any)
    suspend fun remove(id: String)
    suspend fun getAllByPage(offset: Int): List<Any>
    suspend fun getById(id: String): Any
}
