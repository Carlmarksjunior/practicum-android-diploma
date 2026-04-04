package ru.practicum.android.diploma.db.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
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

@Entity(tableName = "favorite_vacancies_table")
data class VacancyEntity(
    @PrimaryKey
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

class Converters {
    @TypeConverter
    fun fromList(list: List<String>): String = Json.encodeToString(list)

    @TypeConverter
    fun toList(string: String): List<String> = Json.decodeFromString<List<String>>(string)

    @TypeConverter
    fun fromSalary(salary: Salary?): String? = salary?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toSalary(string: String?): Salary? = string?.let { Json.decodeFromString(it) }

    @TypeConverter
    fun fromAddress(address: Address?): String? = address?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toAddress(string: String?): Address? = string?.let { Json.decodeFromString(it) }

    @TypeConverter
    fun fromEmployer(employer: Employer?): String? = employer?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toEmployer(string: String?): Employer? = string?.let { Json.decodeFromString(it) }

    @TypeConverter
    fun fromExperience(experience: Experience?): String? = experience?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toExperience(string: String?): Experience? = string?.let { Json.decodeFromString(it) }

    @TypeConverter
    fun fromSchedule(schedule: Schedule?): String? = schedule?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toSchedule(string: String?): Schedule? = string?.let { Json.decodeFromString(it) }

    @TypeConverter
    fun fromEmployment(employment: Employment?): String? = employment?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toEmployment(string: String?): Employment? = string?.let { Json.decodeFromString(it) }

    @TypeConverter
    fun fromContacts(contacts: Contacts?): String? = contacts?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toContacts(string: String?): Contacts? = string?.let { Json.decodeFromString(it) }

    @TypeConverter
    fun fromArea(area: Area?): String? = area?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toArea(string: String?): Area? = string?.let { Json.decodeFromString(it) }

    @TypeConverter
    fun fromIndustry(industry: Industry?): String? = industry?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toIndustry(string: String?): Industry? = string?.let { Json.decodeFromString(it) }

    @TypeConverter
    fun fromPhoneList(list: List<Phone>?): String? = list?.let { Json.encodeToString(it) }

    @TypeConverter
    fun toPhoneList(string: String?): List<Phone>? = string?.let { Json.decodeFromString(it) }
}
