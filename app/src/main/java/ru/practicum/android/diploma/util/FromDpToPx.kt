package ru.practicum.android.diploma.util

import android.content.Context

fun Int.fromDpToPx(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}
