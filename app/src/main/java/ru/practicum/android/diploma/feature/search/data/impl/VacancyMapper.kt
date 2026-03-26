package ru.practicum.android.diploma.feature.search.data.impl

import ru.practicum.android.diploma.feature.search.data.dto.VacancyDto
import ru.practicum.android.diploma.feature.search.domain.model.Vacancy

fun VacancyDto.toDomain() = Vacancy(
    id = id,
    name = name,
    salary = salary,
    address = address,
    employer = employer,
    url = url
)
