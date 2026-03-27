package ru.practicum.android.diploma.feature.search.domain.model

data class Vacancy(
    val id: String,
    val name: String,
    val salary: Salary? = null,
    val address: Address? = null,
    val employer: Employer? = null,
    val posterUrl: String? = null
)

data class Salary(
    val id: String? = null,
    val currency: String? = null,
    val from: Int? = null,
    val to: Int? = null
)

data class Address(
    val id: String? = null,
    val city: String? = null,
    val street: String? = null,
    val building: String? = null,
    val raw: String? = null
)

data class Employer(
    val id: String? = null,
    val name: String? = null,
    val logo: String? = null
)
