package ru.practicum.android.diploma.feature.vacancy.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.feature.search.data.NetworkClient
import ru.practicum.android.diploma.feature.search.data.dto.RequestDto
import ru.practicum.android.diploma.feature.vacancy.data.ExternalNavigator
import ru.practicum.android.diploma.feature.vacancy.data.dto.VacancyDetailResponse
import ru.practicum.android.diploma.feature.vacancy.domain.api.VacancyRepository
import ru.practicum.android.diploma.feature.vacancy.domain.model.VacancyDetail
import ru.practicum.android.diploma.util.Resource

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyDetailMapper: VacancyDetailMapper,
    private val externalNavigator: ExternalNavigator
) : VacancyRepository {
    override fun getVacancyDetail(id: String): Flow<Resource<VacancyDetail?>> = flow {
        val response = networkClient.doRequest(RequestDto.Vacancy(id))
        when (response.code) {
            SUCCESS_CODE -> {
                val data = response as VacancyDetailResponse
                emit(Resource.Success(vacancyDetailMapper.map(data)))
            }

            NO_INTERNET_CODE -> emit(Resource.Error("Проверьте подключение к интернету"))
            else -> emit(Resource.Error("Ошибка сервера"))
        }
    }

    override fun sendVacancyViaMessenger(url: String) {
        externalNavigator.share(url)
    }

    override fun selectEmailClientAndSend(email: String) {
        externalNavigator.sendEmail(email)
    }

    override fun showCallAppsAndDial(phoneNumber: String) {
        externalNavigator.makeCall(phoneNumber)
    }

    companion object {
        private const val NO_INTERNET_CODE = -1
        private const val SUCCESS_CODE = 200
    }
}
