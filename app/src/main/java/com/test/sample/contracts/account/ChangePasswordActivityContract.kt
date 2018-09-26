package com.farmdrop.customer.contracts.account

import uk.co.farmdrop.library.ui.base.MvpView

interface ChangePasswordActivityContract {

    interface View : MvpView {
        fun closeChangePasswordFragment()
    }
}