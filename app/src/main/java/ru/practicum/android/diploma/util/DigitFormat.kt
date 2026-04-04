package ru.practicum.android.diploma.util

import java.text.DecimalFormat

fun Int.digitFormat(): String {
    return DecimalFormat("#,###").format(this).replace(',', ' ')
}
