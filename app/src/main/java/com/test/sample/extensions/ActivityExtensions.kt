package com.farmdrop.customer.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import com.farmdrop.customer.utils.constants.UrlConstants

fun Activity.openUrl(url: String): Boolean {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    return try {
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}

fun Activity.openGooglePlayAppPage(appIdArg: String? = null): Boolean {
    val appId = appIdArg ?: packageName
    val marketIntent = Intent(Intent.ACTION_VIEW, Uri.parse(UrlConstants.GOOGLE_PLAY_MARKET_URL + appId))
    // add flags to come back to our app when pressing back from Google Play app.
    marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
            Intent.FLAG_ACTIVITY_MULTIPLE_TASK or
            Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
    return try {
        startActivity(marketIntent)
        true
    } catch (e: ActivityNotFoundException) {
        openUrl(UrlConstants.GOOGLE_PLAY_HTTP_URL + appId)
    }
}
