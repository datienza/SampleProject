package com.farmdrop.customer.contracts.session

import uk.co.farmdrop.library.resolution.Resolution

class SignUpFragmentContract {

    interface View : OpenSessionContract.View {
        fun showSignUpFailure()
        fun signUpSuccess()
        fun showDeliveryAreaNotFound()
        fun getResolution(): Resolution
        fun showPostcodeEmptyError()
        fun showPostcodeInvalidError()

        fun showProgressLayout()
        fun showSignUpLayout()
    }
}