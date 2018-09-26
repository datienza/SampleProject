package com.farmdrop.customer.contracts.delivery

import android.support.annotation.StringRes
import com.farmdrop.customer.data.model.delivery.DeliverySlotData
import uk.co.farmdrop.library.ui.base.MvpView

interface DatePickerTimeSlotsListsForDurationViewContract {
    interface View : MvpView {

        fun showDuration(@StringRes durationText: Int, duration: Float)
        fun showEtaMessage()
        fun hideEtaMessage()

        fun displayDeliverySlots(deliverySlots: List<DeliverySlotData>)
    }
}