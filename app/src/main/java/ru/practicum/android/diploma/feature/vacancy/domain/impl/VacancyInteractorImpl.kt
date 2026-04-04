package ru.practicum.android.diploma.feature.vacancy.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.feature.vacancy.domain.api.VacancyRepository
import ru.practicum.android.diploma.feature.vacancy.domain.model.Vacancy
import ru.practicum.android.diploma.util.Resource

class VacancyInteractorImpl(private val vacancyRepository: VacancyRepository) : VacancyInteractor {
    override fun getVacancyDetail(id: String): Flow<Resource<Vacancy?>> {
        return vacancyRepository.getVacancyDetail(id)
    }

    override fun sendVacancyViaMessenger(url: String) {
        vacancyRepository.sendVacancyViaMessenger(url)
    }

    override fun selectEmailClientAndSend(email: String) {
        vacancyRepository.selectEmailClientAndSend(email)
    }

    override fun showCallAppsAndDial(phoneNumber: String) {
        vacancyRepository.showCallAppsAndDial(phoneNumber)
    }
}
