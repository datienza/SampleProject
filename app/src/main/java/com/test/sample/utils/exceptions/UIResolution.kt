package com.farmdrop.customer.utils.exceptions

import com.farmdrop.customer.utils.constants.ConnectionConstants

open class UIResolution(private val uiResolver: UIResolver) : ResolutionByCase() {

    override fun onConnectivityAvailable() =
            uiResolver.hideConnectionError()

    override fun onConnectivityUnavailable() =
            uiResolver.showConnectionError(ConnectionConstants.WIFI_NOT_CONNECTED)

    override fun onServerError(errorType: String, errorMessage: String) =
            uiResolver.showServerError(errorMessage)

    override fun onClientError(errorType: String, errorMessage: String) =
        uiResolver.showClientError(errorMessage)

    override fun onGenericRxException(throwable: Throwable) {
        // nothing for now
    }

    override fun onIOException(throwable: Throwable) =
        uiResolver.showConnectionError(ConnectionConstants.INTERNET_NOT_CONNECTED)

    override fun onNetworkLocationError() {
        // nothing for now
    }
}
