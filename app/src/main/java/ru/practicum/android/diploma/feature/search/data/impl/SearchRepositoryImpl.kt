package ru.practicum.android.diploma.feature.search.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.feature.filter.domain.model.SearchFilters
import ru.practicum.android.diploma.feature.search.data.NetworkClient
import ru.practicum.android.diploma.feature.search.data.dto.RequestDto
import ru.practicum.android.diploma.feature.search.data.dto.VacancySearchResponse
import ru.practicum.android.diploma.feature.search.domain.api.SearchRepository
import ru.practicum.android.diploma.feature.search.domain.model.Vacancy
import ru.practicum.android.diploma.util.Resource

class SearchRepositoryImpl(
    private val networkClient: NetworkClient
) : SearchRepository {
    override fun searchVacancies(
        expression: String,
        filter: SearchFilters?,
        pager: Int
    ): Flow<Resource<List<Vacancy>>> = flow {
        val filters = mutableMapOf<String, String>()
        filters["text"] = expression
        filters["page"] = pager.toString()
        filter?.let {
            it.areaId?.let { id -> filters["area"] = id }
            it.industryId?.let { id -> filters["industry"] = id }
            it.salary?.let { s -> filters["salary"] = s.toString() }
            if (it.isOnlyWithSalary) filters["only_with_salary"] = "true"
        }

        val response = networkClient.doRequest(RequestDto.FilteredVacancies(filters))

        when (response.code) {
            SUCCESS_CODE -> {
                val data = (response as VacancySearchResponse).vacancies.map { it.toDomain() }
                emit(Resource.Success(data))
            }

            NO_INTERNET_CODE -> emit(Resource.Error("Проверьте подключение к интернету"))
            else -> emit(Resource.Error("Ошибка сервера"))
        }
    }

    companion object {
        private const val NO_INTERNET_CODE = -1
        private const val SUCCESS_CODE = 200
    }
}
