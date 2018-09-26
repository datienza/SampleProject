package com.farmdrop.customer.contracts.common

import uk.co.farmdrop.library.ui.base.MvpView

interface CardDetailsViewContract {
    interface View : MvpView {
        fun setCardDetailsToAddNewCard()
        fun setCardDetailsWithCardData(cardBrand: String, last4: String)
    }
}