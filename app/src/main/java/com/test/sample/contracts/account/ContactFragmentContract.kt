package com.farmdrop.customer.contracts.account

import uk.co.farmdrop.library.ui.base.MvpView

interface ContactFragmentContract {
    interface View : MvpView {
        fun showMobile(mobile: String)
        fun showEmail(email: String)
        fun clearErrors()
        fun showMobileError()
        fun showInvalidMobileError()
        fun showEmailError()
        fun closeFragment()
        fun closeFragmentWithNewContact(mobile: String, email: String)
        fun showContactUpdateError()

        fun setRemoteGDPRText(text: String)
        fun setLocalGDPRText()
    }
}