package ru.practicum.android.diploma.db.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.db.data.entity.VacancyEntity

@Dao
interface VacancyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vacancy: VacancyEntity)

    @Query("DELETE FROM favorite_vacancies_table WHERE id = :id")
    suspend fun remove(id: String)

    /**Метод возвращает не более limit строк таблицы, пропуская при этом первые offset строк*/
    @Query("SELECT * FROM favorite_vacancies_table ORDER BY rowid LIMIT :limit OFFSET :offset")
    fun getAllByPage(offset: Int, limit: Int): Flow<List<VacancyEntity>>

    @Query("SELECT * FROM favorite_vacancies_table WHERE id = :id")
    suspend fun getById(id: String): VacancyEntity

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_vacancies_table WHERE id = :id)")
    suspend fun isFavorite(id: String): Boolean
}
