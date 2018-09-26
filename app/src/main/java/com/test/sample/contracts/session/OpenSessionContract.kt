package com.farmdrop.customer.contracts.session

import uk.co.farmdrop.library.ui.base.MvpView

interface OpenSessionContract {
    interface View : MvpView {
        fun showEmailEmptyError()
        fun showPasswordLengthFailure()

        fun isPasswordErrorVisible(): Boolean
        fun clearErrors()
        fun clearPasswordError()

        fun trackUserLogInAnalyticsEvent(id: Int, email: String?, firstName: String?, lastName: String?)
    }
}