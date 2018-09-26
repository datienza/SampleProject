package com.farmdrop.customer.contracts.delivery

import uk.co.farmdrop.library.ui.base.MvpView

interface CurrentDeliverySlotViewContract {
    interface View : MvpView {
        fun setChevronImageUp()
        fun setChevronImageDown()

        fun showDeliverySlot(date: String, hours: String)
        fun hideDeliverySlot()

        fun showSelectYourDeliverySlot()
        fun hideSelectYourDeliverySlot()

        fun showProgressBar()
        fun hideProgressBar()

        fun setContentDescription(hours: String, day: String)
    }
}