package ru.practicum.android.diploma.feature.filter.data

interface StorageClient<K> {
    fun <V> storeData(key: K, data: V)
    fun <V> getData(key: K): V?
    fun deleteData(key: K)
    fun clearData()
}
