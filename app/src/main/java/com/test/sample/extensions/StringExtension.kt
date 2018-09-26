package com.farmdrop.customer.extensions

import android.os.Build
import android.text.Html
import android.text.Spanned

fun String.toSpanned(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}

fun String.isInteger(): Boolean {
    return matches("-?\\d+".toRegex())
}

fun String.isNumeric(): Boolean {
    return matches("-?\\d+(\\.\\d+)?".toRegex())
}

val String.Companion.EMPTY: String
    get() { return "" }
