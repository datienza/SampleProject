package com.farmdrop.customer.contracts.home

import com.farmdrop.customer.contracts.common.BottomNavigationActivityContract
import com.farmdrop.customer.data.model.order.OrderData

interface HomeActivityContract {

    interface View : BottomNavigationActivityContract.View {
        fun openSessionActivity()
        fun showLogOutButtonText()
        fun showLogInButtonText()

        fun showDeliverySlotView()
        fun hideDeliverySlotView()
        fun showOrder(orderData: OrderData)
        fun hideOrderLayout()

        fun trackUserLogOutAnalyticsEvent()
    }
}
