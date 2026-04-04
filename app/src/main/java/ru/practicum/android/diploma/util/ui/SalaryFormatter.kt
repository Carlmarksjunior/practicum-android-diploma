package ru.practicum.android.diploma.util.ui

import android.content.Context
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.feature.vacancy.domain.model.Salary
import ru.practicum.android.diploma.util.digitFormat

class SalaryFormatter(private val salary: Salary?, private val context: Context) {
    private val currency: String by lazy {
        val currencySymbol = currencyMap[salary?.currency] ?: -1
        " " + if (currencySymbol != -1) context.getString(currencySymbol) else salary!!.currency ?: ""
    }

    fun format(): String {
        val messageSalaryNull = context.getString(R.string.salary_null)
        if (salary == null || salary.from == null && salary.to == null) return messageSalaryNull
        val from = salary.from?.digitFormat()
        val to = salary.to?.digitFormat()
        return getMessageResult(from, to)
    }

    private fun getMessageResult(from: String?, to: String?): String {
        var messageResult = ""
        if (from != null && to != null) {
            messageResult =
                context.getString(R.string.salary_from_to, from, to) + currency
        }
        if (from != null && to == null) messageResult = context.getString(R.string.salary_from, from) + currency
        if (to != null && from == null) messageResult = context.getString(R.string.salary_to, to) + currency
        return messageResult
    }

    companion object {
        private val currencyMap = mapOf<String, Int>(
            "RUR" to R.string.rub,
            "RUB" to R.string.rub,
            "BYR" to R.string.byr,
            "USD" to R.string.usd,
            "EUR" to R.string.eur,
            "KZT" to R.string.kzt,
            "UAH" to R.string.uah,
            "AZN" to R.string.azn,
            "UZS" to R.string.uzs,
            "GEL" to R.string.gel,
            "KGT" to R.string.kgt,
            "SEK" to R.string.sek,
            "GBP" to R.string.gbp,
            "NZD" to R.string.nzd,
            "SGD" to R.string.sgd,
            "AUD" to R.string.aud,
            "HKD" to R.string.hkd

        )
    }
}
