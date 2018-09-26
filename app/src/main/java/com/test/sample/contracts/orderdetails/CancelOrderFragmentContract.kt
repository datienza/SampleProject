package com.farmdrop.customer.contracts.orderdetails

import uk.co.farmdrop.library.ui.base.MvpView

class CancelOrderFragmentContract {

    interface View : MvpView {
        fun orderCancelled()
    }
}
