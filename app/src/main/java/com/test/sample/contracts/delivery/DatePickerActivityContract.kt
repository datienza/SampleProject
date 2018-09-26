package com.farmdrop.customer.contracts.delivery

import com.farmdrop.customer.data.model.delivery.DeliverySlotData
import uk.co.farmdrop.library.ui.base.MvpView

interface DatePickerActivityContract {
    interface View : MvpView {
        fun finishActivity()
        fun updateCurrentSlot(deliverySlotData: DeliverySlotData)
        fun showDeleteUnavailableItemsDialog()
        fun showUnavailableItems(numUnavailableItems: Int)
        fun hideUnavailableItems()
        fun trackTappedOnDatePickerMainScreenEvent(action: String)
    }
}
