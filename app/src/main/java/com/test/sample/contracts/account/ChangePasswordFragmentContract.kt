package com.farmdrop.customer.contracts.account

import uk.co.farmdrop.library.ui.base.MvpView

interface ChangePasswordFragmentContract {
    interface View : MvpView {
        fun closeFragment()
        fun clearErrors()
        fun showOldPasswordEmptyError()
        fun showNewPasswordEmptyError()
        fun showNewPasswordConfirmationEmptyError()
        fun showNewPasswordLengthError()
        fun showNewPasswordsDifferenceError()
        fun showInvalidOldPassword()
        fun showUpdatePasswordError()
        fun showUpdatePasswordSuccess()
        fun showSendingRequestUi()
        fun hideSendingRequestUi()

        fun onForgottenPasswordClick(email: String)
    }
}