package ru.practicum.android.diploma.feature.vacancy.data.dto

import ru.practicum.android.diploma.feature.search.data.dto.AddressDto
import ru.practicum.android.diploma.feature.search.data.dto.AreaDto
import ru.practicum.android.diploma.feature.search.data.dto.ContactsDto
import ru.practicum.android.diploma.feature.search.data.dto.EmployerDto
import ru.practicum.android.diploma.feature.search.data.dto.EmploymentDto
import ru.practicum.android.diploma.feature.search.data.dto.ExperienceDto
import ru.practicum.android.diploma.feature.search.data.dto.IndustryDto
import ru.practicum.android.diploma.feature.search.data.dto.Response
import ru.practicum.android.diploma.feature.search.data.dto.SalaryDto
import ru.practicum.android.diploma.feature.search.data.dto.ScheduleDto

class VacancyDetailResponse(
    val id: String,
    val name: String,
    val salary: SalaryDto? = null,
    val address: AddressDto? = null,
    val experience: ExperienceDto? = null,
    val schedule: ScheduleDto? = null,
    val employment: EmploymentDto? = null,
    val contacts: ContactsDto? = null,
    val description: String? = null,
    val employer: EmployerDto? = null,
    val area: AreaDto? = null,
    val skills: List<String>? = null,
    val url: String? = null,
    val industry: IndustryDto? = null
) : Response()
