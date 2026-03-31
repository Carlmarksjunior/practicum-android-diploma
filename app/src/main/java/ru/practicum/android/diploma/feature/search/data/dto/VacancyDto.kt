package ru.practicum.android.diploma.feature.search.data.dto

class VacancyDto(
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

data class SalaryDto(
    val id: String? = null,
    val currency: String? = null,
    val from: Int? = null,
    val to: Int? = null
)

data class AddressDto(
    val id: String? = null,
    val city: String? = null,
    val street: String? = null,
    val building: String? = null,
    val raw: String? = null
)

data class ExperienceDto(
    val id: String? = null,
    val name: String? = null
)

data class ScheduleDto(
    val id: String? = null,
    val name: String? = null
)

data class EmploymentDto(
    val id: String? = null,
    val name: String? = null
)

data class ContactsDto(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val phones: List<PhoneDto>? = null
)

data class PhoneDto(
    val comment: String? = null,
    val formatted: String? = null
)

data class EmployerDto(
    val id: String? = null,
    val name: String? = null,
    val logo: String? = null
)

data class AreaDto(
    val id: String? = null,
    val parentId: String? = null,
    val name: String? = null,
    val areas: List<AreaDto>? = null
)

data class IndustryDto(
    val id: String? = null,
    val name: String? = null
)
