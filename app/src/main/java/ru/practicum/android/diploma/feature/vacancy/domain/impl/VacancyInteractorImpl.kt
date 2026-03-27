package ru.practicum.android.diploma.feature.vacancy.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.feature.vacancy.data.model.VacancyDetail
import ru.practicum.android.diploma.feature.vacancy.domain.api.VacancyInteractor
import ru.practicum.android.diploma.feature.vacancy.domain.api.VacancyRepository
import ru.practicum.android.diploma.util.Resource

class VacancyInteractorImpl(private val vacancyRepository: VacancyRepository) : VacancyInteractor {
    override fun getVacancyDetail(id: String): Flow<Resource<VacancyDetail?>> {
        return vacancyRepository.getVacancyDetail(id)
    }

    override fun sendVacancyViaMessenger(url: String) {
        TODO("Not yet implemented")
    }

    override fun selectEmailClientAndSend(email: String) {
        TODO("Not yet implemented")
    }

    override fun showCallAppsAndDial(phoneNumber: String) {
        TODO("Not yet implemented")
    }

}
