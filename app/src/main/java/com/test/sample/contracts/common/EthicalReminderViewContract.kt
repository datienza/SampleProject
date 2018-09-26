package com.farmdrop.customer.contracts.common

import uk.co.farmdrop.library.ui.base.MvpView

interface EthicalReminderViewContract {
    interface View : MvpView {
        fun showTitle(title: String)
        fun showImage(imageUrl: String)
        fun hideView()
    }
}
