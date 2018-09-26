package com.farmdrop.customer.contracts.account

import uk.co.farmdrop.library.ui.base.MvpView

interface ContentFromCMSFragmentContract {
    interface View : MvpView {
        fun showContent(contentAsHtml: String)
    }
}