package ru.practicum.android.diploma.feature.vacancy.data.impl

import ru.practicum.android.diploma.feature.search.data.dto.AreaDto
import ru.practicum.android.diploma.feature.vacancy.data.dto.VacancyDetailResponse
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
import ru.practicum.android.diploma.feature.vacancy.domain.model.VacancyDetail

class VacancyDetailMapper {
    fun map(response: VacancyDetailResponse): VacancyDetail {
        return VacancyDetail(
            id = response.id,
            name = response.name,
            salary = response.salary?.let { Salary(it.id, it.currency, it.from, it.to) },
            address = response.address?.let { Address(it.id, it.city, it.street, it.building, it.raw) },
            experience = response.experience?.let { Experience(it.id, it.name) },
            schedule = response.schedule?.let { Schedule(it.id, it.name) },
            employment = response.employment?.let { Employment(it.id, it.name) },
            contacts = mapContacts(response),
            description = response.description,
            employer = response.employer?.let { Employer(it.id, it.name, it.logo) },
            area = response.area?.let { mapArea(it) },
            skills = response.skills,
            url = response.url,
            industry = response.industry?.let { Industry(it.id, it.name) }
        )
    }

    private fun mapContacts(response: VacancyDetailResponse): Contacts? {
        return response.contacts?.let { contactsDto ->
            Contacts(
                id = contactsDto.id,
                name = contactsDto.name,
                email = contactsDto.email,
                phones = contactsDto.phones?.map { phoneDto ->
                    Phone(phoneDto.comment, phoneDto.formatted)
                }
            )
        }
    }

    private fun mapArea(areaDto: AreaDto): Area {
        return Area(
            id = areaDto.id,
            parentId = areaDto.parentId,
            name = areaDto.name,
            areas = areaDto.areas?.map { mapArea(it) }
        )
    }
}
