package ru.practicum.android.diploma.feature.search.data.impl

import ru.practicum.android.diploma.feature.search.data.dto.AddressDto
import ru.practicum.android.diploma.feature.search.data.dto.AreaDto
import ru.practicum.android.diploma.feature.search.data.dto.ContactsDto
import ru.practicum.android.diploma.feature.search.data.dto.EmployerDto
import ru.practicum.android.diploma.feature.search.data.dto.EmploymentDto
import ru.practicum.android.diploma.feature.search.data.dto.ExperienceDto
import ru.practicum.android.diploma.feature.search.data.dto.IndustryDto
import ru.practicum.android.diploma.feature.search.data.dto.SalaryDto
import ru.practicum.android.diploma.feature.search.data.dto.ScheduleDto
import ru.practicum.android.diploma.feature.search.data.dto.VacancyDto
import ru.practicum.android.diploma.feature.vacancy.domain.model.Address
import ru.practicum.android.diploma.feature.vacancy.domain.model.Area
import ru.practicum.android.diploma.feature.vacancy.domain.model.Contacts
import ru.practicum.android.diploma.feature.vacancy.domain.model.Employer
import ru.practicum.android.diploma.feature.vacancy.domain.model.Employment
import ru.practicum.android.diploma.feature.vacancy.domain.model.Experience
import ru.practicum.android.diploma.feature.vacancy.domain.model.Industry
import ru.practicum.android.diploma.feature.vacancy.domain.model.Phone
import ru.practicum.android.diploma.feature.vacancy.domain.model.Salary
import ru.practicum.android.diploma.feature.vacancy.domain.model.Schedule
import ru.practicum.android.diploma.feature.vacancy.domain.model.Vacancy

fun VacancyDto.toDomain() = Vacancy(
    id = id,
    name = name,
    salary = salary?.toDomain(),
    address = address?.toDomain(),
    experience = experience?.toDomain(),
    schedule = schedule?.toDomain(),
    employment = employment?.toDomain(),
    contacts = contacts?.toDomain(),
    description = description,
    employer = employer?.toDomain(),
    area = area?.toDomain(),
    skills = skills,
    url = url,
    industry = industry?.toDomain()
)

fun SalaryDto.toDomain() = Salary(
    id,
    currency,
    from,
    to
)

fun AddressDto.toDomain() = Address(
    id,
    city,
    street,
    building,
    raw
)

fun ExperienceDto.toDomain() = Experience(
    id,
    name
)

fun ScheduleDto.toDomain() = Schedule(
    id,
    name
)

fun EmploymentDto.toDomain() = Employment(
    id,
    name
)

fun ContactsDto.toDomain() = Contacts(
    id,
    name,
    email,
    phones?.map { Phone(it.comment, it.formatted) }
)

fun EmployerDto.toDomain() = Employer(
    id,
    name,
    logo
)

fun AreaDto.toDomain() = Area(
    id,
    parentId,
    name,
    areas?.map { Area(it.id, it.parentId, it.name) }
)

fun IndustryDto.toDomain() = Industry(
    id,
    name
)
