package com.farmdrop.customer.contracts.session

import uk.co.farmdrop.library.resolution.Resolution
import uk.co.farmdrop.library.ui.base.MvpView

class ForgottenPasswordFragmentContract {

    interface View : MvpView {
        fun showPasswordError()
        fun resetPasswordSuccess()
        fun showResetPasswordFailed()
        fun getResolution(): Resolution
    }
}