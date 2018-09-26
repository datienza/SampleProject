package com.farmdrop.customer.contracts.postcodechecker

import uk.co.farmdrop.library.resolution.Resolution
import uk.co.farmdrop.library.ui.base.MvpView

interface PostcodeCheckerEmailFragmentContract {
    interface View : MvpView {
        fun showConfirmationLayout()
        fun setRemoteTitle(title: String)
        fun setRemoteSubTitle(subTitle: String)
        fun setLocalTitle()
        fun setLocalSubTitle()
        fun setLocalConfirmationTitle()
        fun setRemoteConfirmationTitle(thanksTitle: String)
        fun setLocalConfirmationSubTitle()
        fun setRemoteConfirmationSubTitle(thanksCopy: String)
        fun setLocalSubmitButtonText()
        fun setRemoteSubmitButtonText(buttonText: String)
        fun subscribeEmailError()
        fun emptyEmailError()
        fun getResolution(): Resolution
        fun showBrowseButton()
        fun hideSubmitButton()
        fun showSubmitButton()
        fun hideBrowseButton()
    }
}
