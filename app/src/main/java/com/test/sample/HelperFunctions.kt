package com.farmdrop.customer

private const val HUNDRED_FLOAT = 100.0f

fun penceToPounds(pence: Int) = pence.div(HUNDRED_FLOAT)

fun poundsToPence(pounds: Float): Int {
    val valueInPounds = pounds.times(HUNDRED_FLOAT)
    return Math.round(valueInPounds)
}
