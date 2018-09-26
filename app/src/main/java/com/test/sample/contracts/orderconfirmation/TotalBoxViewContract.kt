package com.farmdrop.customer.contracts.orderconfirmation

import uk.co.farmdrop.library.ui.base.MvpView

interface TotalBoxViewContract {

    interface View : MvpView {
        fun showPiggyBankCreditText(discountCredit: Float)
        fun showPromoCreditText(discountCredit: Float)
        fun showFreeDelivery()
        fun formatAndDisplayDeliveryCost(deliveryCost: Int)
    }
}
