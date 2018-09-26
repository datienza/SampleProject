package com.farmdrop.customer.contracts.agecheck

import android.support.annotation.StringRes
import uk.co.farmdrop.library.ui.base.MvpView

interface AgeCheckActivityContract {
    interface View : MvpView {
        fun goToOrderSummary()

        fun finishActivity()

        fun setToolbarIconAsBack()

        fun setToolbarIconAsCross()

        fun setTitleText(@StringRes stringId: Int)

        fun setToRemoveAlcoholFragment()

        fun popFragment()
    }
}