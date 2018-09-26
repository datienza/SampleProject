package com.farmdrop.customer.contracts.common

import uk.co.farmdrop.library.ui.base.MvpView

interface AddressDetailsViewContract {
    interface View : MvpView {
        fun showAddressLinePreview(address1: String, zipcode: String)
        fun showEnterDeliveryAddressText()
        fun hideChevron()
        fun showProgressBar()
        fun hideProgressBar()
        fun showAddressTextViews()
    }
}
