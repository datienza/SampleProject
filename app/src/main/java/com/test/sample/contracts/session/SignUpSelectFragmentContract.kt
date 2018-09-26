package com.farmdrop.customer.contracts.session

class SignUpSelectFragmentContract {

    interface View : BaseSessionSelectFragmentContract.View {
        fun onSignUpWithFacebookSuccess()
        fun onSignUpWithFacebookError(errorMessage: String? = null)
        fun getPostcodeAndSignUp()
    }
}
