package ru.practicum.android.diploma.feature.search.data.impl

import ru.practicum.android.diploma.feature.search.data.dto.VacancyDto
import ru.practicum.android.diploma.feature.search.domain.model.Address
import ru.practicum.android.diploma.feature.search.domain.model.Employer
import ru.practicum.android.diploma.feature.search.domain.model.Salary
import ru.practicum.android.diploma.feature.search.domain.model.Vacancy

fun VacancyDto.toDomain() = Vacancy(
    id = id,
    name = name,
    salary = Salary(
        salary?.id,
        salary?.currency,
        salary?.from,
        salary?.to
    ),
    address = Address(
        address?.id,
        address?.city,
        address?.street,
        address?.building,
        address?.raw
    ),
    employer = Employer(
        employer?.id,
        employer?.name,
        employer?.logo
    ),
    posterUrl = url
)
