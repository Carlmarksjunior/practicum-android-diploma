package ru.practicum.android.diploma.util.ui

class DescriptionFormatterVacancy(private val description: String?) {

    fun format(): CharSequence {
        return description ?: ""
    }
}
