package ru.practicum.android.diploma.feature.vacancy.data.impl


import ru.practicum.android.diploma.feature.vacancy.data.dto.VacancyDetailResponse
import ru.practicum.android.diploma.feature.vacancy.data.model.VacancyDetail

class VacancyDetailMapper() {
    fun map(vacancyDetailResponse: VacancyDetailResponse): VacancyDetail {
        return VacancyDetail(
            id = vacancyDetailResponse.id,
            name = vacancyDetailResponse.name,
            salary = vacancyDetailResponse.salary,
            address = vacancyDetailResponse.address,
            experience = vacancyDetailResponse.experience,
            schedule = vacancyDetailResponse.schedule,
            employment = vacancyDetailResponse.employment,
            contacts = vacancyDetailResponse.contacts,
            description = vacancyDetailResponse.description,
            employer = vacancyDetailResponse.employer,
            area = vacancyDetailResponse.area,
            skills = vacancyDetailResponse.skills,
            url = vacancyDetailResponse.url,
            industry = vacancyDetailResponse.industry
        )
    }
}

