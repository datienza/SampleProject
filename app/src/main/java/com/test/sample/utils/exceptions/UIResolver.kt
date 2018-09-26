package com.farmdrop.customer.utils.exceptions

import android.content.Context
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.farmdrop.customer.R
import uk.co.farmdrop.library.extensions.getColour
import com.farmdrop.customer.utils.constants.ConnectionConstants
import timber.log.Timber

class UIResolver(private val activity: AppCompatActivity) {

    val context: Context
        get() = activity

    fun showConnectionError(@ConnectionConstants.ConnectionStatus code: Int) {
        // TODO show layout/snackbar when offline
        Timber.e("No conection with code: %s", code)
    }

    fun hideConnectionError() {
    }

    fun showClientError(errorMessage: String) {
        val parentLayout = activity.findViewById<View>(android.R.id.content)
        val snackbar = Snackbar.make(parentLayout, errorMessage, Snackbar.LENGTH_LONG)
        snackbar.setActionTextColor(Color.WHITE)
        snackbar.view.setBackgroundColor(activity.getColour(R.color.bright_red))
        snackbar.show()
    }

    fun showServerError(errorMessage: String) {
        val parentLayout = activity.findViewById<View>(android.R.id.content)
        Snackbar.make(parentLayout, errorMessage, Snackbar.LENGTH_LONG).show()
    }
}
