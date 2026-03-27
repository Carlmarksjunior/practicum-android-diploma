package ru.practicum.android.diploma.feature.vacancy.data

interface ExternalNavigator {
    fun share(link: String)

    fun sendEmail(email: String)
    fun makeCall(phoneNumber: String)
}
