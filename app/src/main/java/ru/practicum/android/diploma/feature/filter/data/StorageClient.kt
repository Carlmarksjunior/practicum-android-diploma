package ru.practicum.android.diploma.feature.filter.data

interface StorageClient<T> {
    fun storeData(data: T)
    fun getData(): T?
    fun clearData()
}
