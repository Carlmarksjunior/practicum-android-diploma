package ru.practicum.android.diploma.feature.filter.data.impl

import com.google.gson.Gson
import ru.practicum.android.diploma.feature.filter.data.StorageClient
import ru.practicum.android.diploma.feature.filter.data.storage.SharedPrefsStorageClient
import ru.practicum.android.diploma.feature.filter.domain.api.FiltersSavingRepository
import ru.practicum.android.diploma.feature.filter.domain.model.AreaCountry
import ru.practicum.android.diploma.feature.filter.domain.model.AreaRegion
import ru.practicum.android.diploma.feature.vacancy.domain.model.Industry
import java.lang.reflect.Type

class FiltersSavingRepositoryImpl(
    private val storageClient: StorageClient<String>,
    private val gson: Gson,
    private val areaCountryType: Type,
    private val areaRegionType: Type,
    private val industryType: Type
) : FiltersSavingRepository {
    override fun setCountry(country: AreaCountry) {
        storageClient.storeData(
            SharedPrefsStorageClient.AREA_COUNTRY_KEY,
            gson.toJson(country, areaCountryType)
        )
    }

    override fun setRegion(region: AreaRegion) {
        storageClient.storeData(
            SharedPrefsStorageClient.AREA_REGION_KEY,
            gson.toJson(region, areaRegionType)
        )
    }

    override fun setIndustry(industry: Industry) {
        storageClient.storeData(
            SharedPrefsStorageClient.INDUSTRY_KEY,
            gson.toJson(industry, industryType)
        )
    }

    override fun setSalary(salary: Int) {
        storageClient.storeData(SharedPrefsStorageClient.SALARY_KEY, salary)
    }

    override fun setIsOnlyWithSalary(isOnlyWithSalary: Boolean) {
        storageClient.storeData(SharedPrefsStorageClient.ONLY_WITH_SALARY_KEY, isOnlyWithSalary)
    }
}
