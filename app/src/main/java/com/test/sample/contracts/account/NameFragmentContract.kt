package com.farmdrop.customer.contracts.account

import uk.co.farmdrop.library.ui.base.MvpView

interface NameFragmentContract {
    interface View : MvpView {
        fun showFirstName(firstName: String)
        fun showLastName(lastName: String)
        fun clearErrors()
        fun showFirstNameError()
        fun showLastNameError()
        fun closeFragment()
        fun closeFragmentWithNewName(firstName: String, lastName: String)
        fun showNameUpdateError()

        fun setRemoteGDPRText(text: String)
        fun setLocalGDPRText()
    }
}