package com.farmdrop.customer.contracts.orderconfirmation

import com.farmdrop.customer.data.model.order.OrderData
import com.farmdrop.customer.data.model.order.OrderDetailsContentItemInfo
import uk.co.farmdrop.library.ui.base.MvpView
import java.util.Date

interface OrderDetailsActivityContract {

    interface View : MvpView {
        fun displayDeliveryStatusHarvested()
        fun startTimeRemainingCountdown(targetDate: Date)
        fun showStatusInfoTextView()
        fun setListAdapter()
        fun addCancelButtonToContentList()
        fun addDeliveryDetailsToContentList(position: Int, orderData: OrderData)
        fun setContentList(orderData: OrderData, orderLineItems: List<OrderDetailsContent.OrderDetailsContentItem>)
        fun showAddAllToBasketButton()
        fun showItemsAddedToBasket()
        fun showOutOfStockItemsInBasket(outOfStockProducts: List<OrderDetailsContentItemInfo>)
        fun showProgressBar()
        fun hideProgressBar()
        fun notifyItemChanged(position: Int)
    }
}
