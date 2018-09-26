package com.farmdrop.customer.contracts.account

import uk.co.farmdrop.library.ui.base.MvpView

interface FAQsFragmentContract {
    interface View : MvpView {
        fun showContent(contentAsHtml: String)
        fun showError()
    }
}