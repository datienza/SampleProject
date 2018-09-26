package com.farmdrop.customer.contracts.session

import com.farmdrop.customer.utils.exceptions.LogInResolution

class LogInSelectFragmentContract {

    interface View : BaseSessionSelectFragmentContract.View {
        fun onLogInWithFacebookError(errorMessage: String? = null)
        fun onLogInWithFacebookSuccess()
        fun getResolution(): LogInResolution
        fun getPostcodeAndLogIn()
    }
}
