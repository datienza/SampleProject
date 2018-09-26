package com.farmdrop.customer.contracts.session

import uk.co.farmdrop.library.resolution.Resolution

class LogInFragmentContract {

    interface View : OpenSessionContract.View {
        fun showLogInFailure()
        fun logInSuccess()
        fun getResolution(): Resolution
        fun showProgressLayout()
        fun showLoginLayout()
    }
}