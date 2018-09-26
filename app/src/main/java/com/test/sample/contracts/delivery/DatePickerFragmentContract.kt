package com.farmdrop.customer.contracts.delivery

import com.farmdrop.customer.data.model.delivery.DeliverySlotData
import com.farmdrop.customer.data.model.delivery.OpeningTimes
import com.farmdrop.customer.utils.MutablePair
import uk.co.farmdrop.library.ui.base.MvpView

interface DatePickerFragmentContract {
    interface View : MvpView {

        fun hideDatePickerLayoutWithoutAnimation()
        fun hideDatePickerLayout()
        fun showDatePickerLayout()
        fun populateDeliverySlots(daysTopHeader: List<String>, openingTimesStartHeader: List<OpeningTimes>, deliverySlots: List<MutablePair<Float, List<MutablePair<String, List<DeliverySlotData>>>>>)

        fun showErrorLoadingDeliverySlots()

        fun showLoadingLayoutWithoutAnimation()
        fun showLoadingLayout()
        fun hideLoadingLayout()

        fun trackDatePickerViewedEvent(nbAvailableSlots: Int)
    }
}
