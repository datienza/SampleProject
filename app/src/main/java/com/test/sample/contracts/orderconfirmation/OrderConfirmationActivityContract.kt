package com.farmdrop.customer.contracts.orderconfirmation

import com.farmdrop.customer.data.model.order.OrderData
import uk.co.farmdrop.library.ui.base.MvpView

interface OrderConfirmationActivityContract {

    interface View : MvpView {
        fun showSubheading(subHeading: String)
        fun showOrderThanksHeader(userName: String)

        fun showOrderLayout()
        fun hideOrderLayout()
        fun showNoOrder()
        fun showOrder(orderData: OrderData)

        fun showLoadingOrderProgressBar()
        fun hideLoadingOrderProgressBar()
        fun showOrderError()
        fun showCreditTitle(credit: Int)
    }
}