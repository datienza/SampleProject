package com.farmdrop.customer.contracts.account

import uk.co.farmdrop.library.ui.base.MvpView
import java.util.Date

interface OrderViewContract {
    interface View : MvpView {
        fun displayDeliveryDate(deliveryDate: String)
        fun displayDeliveryWindow(deliveryWindow: String)
        fun startTimeRemainingCountdown(targetDate: Date)
        fun displayDeliveryStatusHarvested()
        fun displayDeliveryStatusDelivered(deliveryTime: String)
        fun displayDeliveryStatusCancelled()
    }
}