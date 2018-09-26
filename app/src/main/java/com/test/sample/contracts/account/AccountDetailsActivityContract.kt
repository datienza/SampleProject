package com.farmdrop.customer.contracts.account

import android.support.annotation.StringRes
import uk.co.farmdrop.library.ui.base.MvpView

interface AccountDetailsActivityContract {
    interface View : MvpView {
        fun setTitleText(@StringRes title: Int)
        fun getCurrentFragment()
        fun popBackStack()

        fun setToNameFragment()
        fun setToContactFragment()
        fun setToAddressFragment()
        fun setAddressPreview()

        fun checkAndSendUserNameUpdate()
        fun checkAndSendContactUpdate()
        fun checkAndSendAddressUpdate()
        fun finishActivity()
    }
}
