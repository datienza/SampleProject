package com.farmdrop.customer.utils.constants

import android.support.annotation.IntDef

object ConnectionConstants {

    const val WIFI_NOT_CONNECTED = 1
    const val INTERNET_NOT_CONNECTED = 2
    const val API_NOT_WORKING = 3

    @IntDef(WIFI_NOT_CONNECTED, INTERNET_NOT_CONNECTED, API_NOT_WORKING)
    @Retention(AnnotationRetention.SOURCE)
    annotation class ConnectionStatus
}
