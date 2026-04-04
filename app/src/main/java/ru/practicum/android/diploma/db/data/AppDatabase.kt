package ru.practicum.android.diploma.db.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.practicum.android.diploma.db.data.dao.VacancyDao
import ru.practicum.android.diploma.db.data.entity.Converters
import ru.practicum.android.diploma.db.data.entity.VacancyEntity

@Database(entities = [VacancyEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getVacancyDao(): VacancyDao
}
