package ru.practicum.android.diploma.feature.vacancy.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.vacancy.domain.model.Vacancy
import ru.practicum.android.diploma.util.Resource

interface VacancyInteractor {
    fun getVacancyDetail(id: String): Flow<Resource<Vacancy?>>
    fun sendVacancyViaMessenger(url: String)
    fun selectEmailClientAndSend(email: String)
    fun showCallAppsAndDial(phoneNumber: String)
}
