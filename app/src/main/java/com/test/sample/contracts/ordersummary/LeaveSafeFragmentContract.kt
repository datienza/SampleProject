package com.farmdrop.customer.contracts.ordersummary

import uk.co.farmdrop.library.ui.base.MvpView

interface LeaveSafeFragmentContract {
    interface View : MvpView {
        fun showLeaveSafePolicy(policyAsHtml: String)
        fun hideLeaveSafePolicy()

        fun showProgressBar()
        fun hideProgressBar()

        fun showError()
        fun hideError()

        fun hideButtons()
        fun showButtonsProgressBar()
        fun showButtons()
        fun hideButtonsProgressBar()

        fun onAgreeLeaveSafePolicyUpdated()
    }
}