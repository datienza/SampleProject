package com.farmdrop.customer.contracts.session

import uk.co.farmdrop.library.ui.base.MvpView

interface SessionActivityContract {

    interface View : MvpView {
        fun onCloseSession()
        fun onRestoreInitialFragment()
    }
}