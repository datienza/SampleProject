package com.farmdrop.customer.contracts.common

import uk.co.farmdrop.library.ui.base.MvpView

interface OrderStatusBarViewContract {
    interface View : MvpView {
        fun showOrderOpened()
        fun showOrderHarvested()
        fun showOrderDelivered()
        fun showOrderCancelled()
        fun showDeliveryAtText(deliveryAt: String)
    }
}
