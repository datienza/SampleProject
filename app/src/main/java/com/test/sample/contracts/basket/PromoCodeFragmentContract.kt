package com.farmdrop.customer.contracts.basket

import uk.co.farmdrop.library.ui.base.MvpView

class PromoCodeFragmentContract {

    interface View : MvpView {
        fun clearPromoCodeError()
        fun showPromoCodeError()
        fun enterPromoCodeSuccess()
        fun enterPromoCodeFailed()
        fun setSubmitButtonEnabled(enabled: Boolean)
        fun isSubmitButtonEnabled(): Boolean
    }
}