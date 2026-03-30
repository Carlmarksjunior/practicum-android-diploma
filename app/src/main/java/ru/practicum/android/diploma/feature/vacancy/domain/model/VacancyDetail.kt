package ru.practicum.android.diploma.feature.vacancy.domain.model

import kotlinx.serialization.Serializable

data class VacancyDetail(
    val id: String,
    val name: String,
    val salary: Salary? = null,
    val address: Address? = null,
    val experience: Experience? = null,
    val schedule: Schedule? = null,
    val employment: Employment? = null,
    val contacts: Contacts? = null,
    val description: String? = null,
    val employer: Employer? = null,
    val area: Area? = null,
    val skills: List<String>? = null,
    val url: String? = null,
    val industry: Industry? = null
)

@Serializable
data class Salary(
    val id: String? = null,
    val currency: String? = null,
    val from: Int? = null,
    val to: Int? = null
)

@Serializable
data class Address(
    val id: String? = null,
    val city: String? = null,
    val street: String? = null,
    val building: String? = null,
    val raw: String? = null
)

@Serializable
data class Experience(
    val id: String? = null,
    val name: String? = null
)

@Serializable
data class Schedule(
    val id: String? = null,
    val name: String? = null
)

@Serializable
data class Employment(
    val id: String? = null,
    val name: String? = null
)

@Serializable
data class Contacts(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val phones: List<Phone>? = null
)

@Serializable
data class Phone(
    val comment: String? = null,
    val formatted: String? = null
)

@Serializable
data class Employer(
    val id: String? = null,
    val name: String? = null,
    val logo: String? = null
)

@Serializable
data class Area(
    val id: String? = null,
    val parentId: String? = null,
    val name: String? = null,
    val areas: List<Area>? = null
)

@Serializable
data class Industry(
    val id: String? = null,
    val name: String? = null
)
