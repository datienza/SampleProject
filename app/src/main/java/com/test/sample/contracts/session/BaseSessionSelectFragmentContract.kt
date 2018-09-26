package com.farmdrop.customer.contracts.session

import uk.co.farmdrop.library.ui.base.MvpView

class BaseSessionSelectFragmentContract {

    interface View : MvpView {
        fun onStartSessionWithFacebookError(errorMessage: String? = null)
    }
}
