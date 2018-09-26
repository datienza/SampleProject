package com.farmdrop.customer.utils.exceptions

import com.farmdrop.customer.utils.constants.ApiErrorConstants

class LogInResolution(uiResolver: UIResolver) : UIResolution(uiResolver) {

    var callback: Callback? = null

    override fun onClientError(errorType: String, errorMessage: String) {
        when (errorType) {
            ApiErrorConstants.API_USER_NOT_FOUND_ERROR -> callback?.onUserNotFoundError()
            else -> super.onClientError(errorType, errorMessage)
        }
    }

    interface Callback {
        fun onUserNotFoundError()
    }
}
