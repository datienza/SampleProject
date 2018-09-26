package com.farmdrop.customer.extensions

import android.content.Context
import com.farmdrop.customer.R
import java.text.SimpleDateFormat

fun SimpleDateFormat.getDayNumberSuffix(context: Context, day: Int): String {
    if (day in 11..13) {
        return context.getString(R.string.day_suffix_th)
    }
    return when (day % 10) {
        1 -> context.getString(R.string.day_suffix_st)
        2 -> context.getString(R.string.day_suffix_nd)
        3 -> context.getString(R.string.day_suffix_rd)
        else -> context.getString(R.string.day_suffix_th)
    }
}
