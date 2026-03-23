package ru.practicum.android.diploma.db.data

import androidx.room.RoomDatabase
import ru.practicum.android.diploma.db.data.dao.VacancyDao

abstract class AppDatabase : RoomDatabase() {
    abstract fun getVacancyDao(): VacancyDao
}
