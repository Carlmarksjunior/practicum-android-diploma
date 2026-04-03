package ru.practicum.android.diploma.feature.filter.data.impl

import ru.practicum.android.diploma.feature.filter.data.StorageClient
import ru.practicum.android.diploma.feature.filter.domain.api.FiltersDeletingRepository

class FiltersDeletingRepositoryImpl(
    private val storageClient: StorageClient<String>
) : FiltersDeletingRepository {
    override fun deleteFilter(key: String) {
        storageClient.deleteData(key)
    }

    override fun clearFilters() {
        storageClient.clearData()
    }
}
