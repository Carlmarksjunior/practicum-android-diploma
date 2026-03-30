package ru.practicum.android.diploma.util

import ru.practicum.android.diploma.db.data.entity.VacancyEntity
import ru.practicum.android.diploma.feature.vacancy.domain.model.VacancyDetail

fun VacancyDetail.toDbEntity(): VacancyEntity {
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

fun VacancyEntity.toVacancyDetail(): VacancyDetail {
    return VacancyDetail(
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
