package com.farmdrop.customer.contracts.postcodechecker

import uk.co.farmdrop.library.ui.base.MvpView

interface PostcodeCheckerActivityContract {

    interface View : MvpView {
        fun goToHomeActivity()
        fun onBackToPostcodeCheckerFragment()
        fun onCloseActivity()
    }
}