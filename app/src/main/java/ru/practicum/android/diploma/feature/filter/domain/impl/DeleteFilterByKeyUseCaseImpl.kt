package ru.practicum.android.diploma.feature.filter.domain.impl

import ru.practicum.android.diploma.feature.filter.domain.api.DeleteFilterByKeyUseCase
import ru.practicum.android.diploma.feature.filter.domain.api.FiltersDeletingRepository

class DeleteFilterByKeyUseCaseImpl(private val repository: FiltersDeletingRepository) : DeleteFilterByKeyUseCase {
    override fun execute(key: String) {
        repository.deleteFilter(key)
    }

    companion object {
        const val AREA_COUNTRY_KEY = "area_country"
        const val AREA_REGION_KEY = "area_region"
        const val INDUSTRY_KEY = "industry"
        const val SALARY_KEY = "salary"
        const val ONLY_WITH_SALARY_KEY = "only_with_salary"
    }
}
