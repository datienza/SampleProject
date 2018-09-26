package com.farmdrop.customer.utils.exceptions

import com.crashlytics.android.Crashlytics
import com.farmdrop.customer.utils.constants.ApiErrorConstants
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import uk.co.farmdrop.library.exceptions.HttpExceptionWrapper
import uk.co.farmdrop.library.resolution.Resolution
import java.io.IOException

abstract class ResolutionByCase : Resolution {
    override fun onHttpException(httpExceptionWrapper: HttpExceptionWrapper) {
        val code = httpExceptionWrapper.responseCode
        val errorObject = getErrorObject(httpExceptionWrapper)
        val errorDescription = getErrorMessage(errorObject)
        val errorType = getErrorType(errorObject)
        if (isClientError(code)) {
            onClientError(errorType, errorDescription)
        } else if (isServerError(code)) {
            onServerError(errorType, errorDescription)
        }
    }

    private fun getErrorObject(httpExceptionWrapper: HttpExceptionWrapper): JSONObject? {
        try {
            return JSONObject(httpExceptionWrapper.responseErrorBody)
        } catch (e: Throwable) {
            when (e) {
                is JSONException, is IOException -> {
                    Crashlytics.logException(e)
                    Timber.e(e)
                }
                else -> throw e
            }
        }
        return null
    }

    private fun getErrorMessage(errorObject: JSONObject?): String {
        try {
            if (errorObject != null && errorObject.has(ApiErrorConstants.API_ERROR_DESCRIPTION_FIELD)) {
                return errorObject.getString(ApiErrorConstants.API_ERROR_DESCRIPTION_FIELD)
            }
        } catch (throwable: JSONException) {
            Crashlytics.logException(throwable)
            Timber.e(throwable)
        }

        return ApiErrorConstants.API_ERROR_MESSAGE_EMPTY
    }

    private fun getErrorType(errorObject: JSONObject?): String {
        try {
            if (errorObject != null && errorObject.has(ApiErrorConstants.API_ERROR_FIELD)) {
                return errorObject.getString(ApiErrorConstants.API_ERROR_FIELD)
            }
        } catch (throwable: JSONException) {
            Crashlytics.logException(throwable)
            Timber.e(throwable)
        }

        return ApiErrorConstants.API_ERROR_MESSAGE_EMPTY
    }

    private fun isClientError(responseCode: Int) = responseCode / 100 == 4

    private fun isServerError(responseCode: Int) = responseCode / 100 == 5

    abstract fun onServerError(errorType: String, errorMessage: String)

    abstract fun onClientError(errorType: String, errorMessage: String)
}
