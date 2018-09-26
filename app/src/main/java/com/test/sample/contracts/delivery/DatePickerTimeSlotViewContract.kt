package com.farmdrop.customer.contracts.delivery

import com.farmdrop.customer.data.model.delivery.DeliverySlotData
import uk.co.farmdrop.library.ui.base.MvpView

interface DatePickerTimeSlotViewContract {
    interface View : MvpView {
        fun showPrice(price: Float)
        fun showFree()
        fun showFull()
        fun showClosed()
        fun showBackgroundNormal()
        fun showBackgroundSelected()
        fun setSelectedView(deliverySlot: DeliverySlotData, actionFromUser: Boolean)
        fun enableClickListener()
        fun disableClickListener()
        fun setContentDescription(hours: String, day: String)
    }
}