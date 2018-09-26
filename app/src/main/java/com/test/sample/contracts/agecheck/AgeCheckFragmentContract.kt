package com.farmdrop.customer.contracts.agecheck

import uk.co.farmdrop.library.ui.base.MvpView

interface AgeCheckFragmentContract {
    interface View : MvpView {
        fun onUserConfirmedOverEighteen()
        fun onUserConfirmedUnderEighteen()
    }
}