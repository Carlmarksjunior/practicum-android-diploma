package ru.practicum.android.diploma.feature.filter.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import ru.practicum.android.diploma.feature.filter.data.StorageClient

class SharedPrefsStorageClient(private val sharedPreferences: SharedPreferences) : StorageClient<String> {
    override fun <V> storeData(key: String, data: V) {
        sharedPreferences.edit {
            when (data) {
                is String -> putString(key, data)
                is Int -> putInt(key, data)
                is Boolean -> putBoolean(key, data)
                else -> throw IllegalArgumentException("Unsupported data type")
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V> getData(key: String): V? {
        val result: V? = when (key) {
            AREA_COUNTRY_KEY -> tryGetString(key) as? V
            AREA_REGION_KEY -> tryGetString(key) as? V
            INDUSTRY_KEY -> tryGetString(key) as? V
            SALARY_KEY -> tryGetInt(key) as? V
            ONLY_WITH_SALARY_KEY -> tryGetBoolean(key) as? V
            else -> null
        }
        return result
    }

    override fun deleteData(key: String) {
        sharedPreferences.edit {
            remove(key)
        }
    }

    override fun clearData() {
        sharedPreferences.edit { clear() }
    }

    private fun tryGetString(key: String): String? {
        val result = sharedPreferences.getString(key, "")
        return if (result.isNullOrEmpty()) {
            null
        } else {
            result
        }
    }

    private fun tryGetInt(key: String): Int? {
        val result = sharedPreferences.getInt(key, -1)
        return if (result == -1) null else result
    }

    private fun tryGetBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    companion object {
        const val AREA_COUNTRY_KEY = "area_country"
        const val AREA_REGION_KEY = "area_region"
        const val INDUSTRY_KEY = "industry"
        const val SALARY_KEY = "salary"
        const val ONLY_WITH_SALARY_KEY = "only_with_salary"
    }
}
