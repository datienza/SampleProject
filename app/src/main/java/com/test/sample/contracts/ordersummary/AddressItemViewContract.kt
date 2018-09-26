package com.farmdrop.customer.contracts.ordersummary

import uk.co.farmdrop.library.ui.base.MvpView

interface AddressItemViewContract {
    interface View : MvpView {
        fun showTickImage()

        fun hideTickImage()

        fun setAddress(address: String)

        fun getCompleteAddress(addressData: AddressData): String

        fun getSingleLineAddress(addressData: AddressData): String
    }
}
