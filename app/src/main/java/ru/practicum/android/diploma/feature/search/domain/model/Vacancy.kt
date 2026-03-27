package ru.practicum.android.diploma.feature.search.domain.model

import ru.practicum.android.diploma.feature.search.data.dto.AddressDto
import ru.practicum.android.diploma.feature.search.data.dto.EmployerDto
import ru.practicum.android.diploma.feature.search.data.dto.SalaryDto

data class Vacancy(
    val id: String,
    val name: String,
    val salary: SalaryDto? = null,
    val address: AddressDto? = null,
    val employer: EmployerDto? = null,
    val posterUrl: String? = null
)
