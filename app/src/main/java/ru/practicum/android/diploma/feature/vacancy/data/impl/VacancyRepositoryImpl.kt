package ru.practicum.android.diploma.feature.vacancy.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.feature.search.data.NetworkClient
import ru.practicum.android.diploma.feature.search.data.dto.RequestDto
import ru.practicum.android.diploma.feature.vacancy.data.dto.VacancyDetailResponse
import ru.practicum.android.diploma.feature.vacancy.data.model.VacancyDetail
import ru.practicum.android.diploma.feature.vacancy.domain.api.VacancyRepository
import ru.practicum.android.diploma.util.Resource

class VacancyRepositoryImpl(
    private val networkClient: NetworkClient,
    private val vacancyDetailMapper: VacancyDetailMapper
) : VacancyRepository {
    override fun getVacancyDetail(id: String): Flow<Resource<VacancyDetail?>> = flow {
        val response = networkClient.doRequest(RequestDto.WithPathId(id))
        when (response.code) {
            SUCCESS_CODE -> {
                val data = (response as VacancyDetailResponse)
                emit(Resource.Success(vacancyDetailMapper.map(data)))
            }

            NO_INTERNET_CODE -> emit(Resource.Error("Проверьте подключение к интернету"))
            else -> emit(Resource.Error("Ошибка сервера"))
        }
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

    companion object {
        private const val NO_INTERNET_CODE = -1
        private const val SUCCESS_CODE = 200
    }
}
