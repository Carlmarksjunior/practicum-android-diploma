package ru.practicum.android.diploma.feature.vacancy.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.vacancy.domain.model.VacancyDetail
import ru.practicum.android.diploma.util.Resource

interface VacancyRepository {
    fun getVacancyDetail(id: String): Flow<Resource<VacancyDetail?>>
    fun sendVacancyViaMessenger(url: String)
    fun selectEmailClientAndSend(email: String)
    fun showCallAppsAndDial(phoneNumber: String)
}
