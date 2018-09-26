package com.farmdrop.customer.contracts.ordersummary

import uk.co.farmdrop.library.ui.base.MvpView

interface OrderSummaryDetailsFragmentContract {
    interface View : MvpView {

        fun setLeaveSafeChecked(checked: Boolean)

        fun showLeaveSafePolicy()

        fun showAlcoholInOrderWarning()
    }
}