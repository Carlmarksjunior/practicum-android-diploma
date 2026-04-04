package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.db.data.entity.VacancyEntity
import ru.practicum.android.diploma.feature.vacancy.domain.model.Vacancy

fun Vacancy.toDbEntity(): VacancyEntity {
    return VacancyEntity(
        id,
        name,
        salary,
        address,
        experience,
        schedule,
        employment,
        contacts,
        description,
        employer,
        area,
        skills,
        url,
        industry
    )
}

fun VacancyEntity.toVacancyDetail(): Vacancy {
    return Vacancy(
        id,
        name,
        salary,
        address,
        experience,
        schedule,
        employment,
        contacts,
        description,
        employer,
        area,
        skills,
        url,
        industry
    )
}
