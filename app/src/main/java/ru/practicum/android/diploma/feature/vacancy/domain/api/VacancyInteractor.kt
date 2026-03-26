package ru.practicum.android.diploma.feature.vacancy.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.vacancy.data.model.VacancyDetail

interface VacancyInteractor {
    fun getVacancyDetail(id: String): Flow<VacancyDetail?>
    fun shareVacancy()
}
