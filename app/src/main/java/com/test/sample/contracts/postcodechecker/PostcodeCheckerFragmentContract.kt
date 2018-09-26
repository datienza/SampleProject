package com.farmdrop.customer.contracts.postcodechecker

import uk.co.farmdrop.library.resolution.Resolution
import uk.co.farmdrop.library.ui.base.MvpView

interface PostcodeCheckerFragmentContract {
    interface View : MvpView {
        fun setRemoteTitle(title: String)
        fun setRemoteSubTitle(subTitle: String)
        fun setLocalTitle()
        fun setLocalSubTitle()
        fun getResolution(): Resolution
        fun showLoadingLayout()
        fun hideLoadingLayout()
        fun onPostcodeCheckerError()
        fun showConfirmationLayout()
        fun setRemoteTitlePostcodeValidationError(validationMessage: String)
        fun setLocalTitlePostcodeValidationError()
        fun setSubtitlePostcodeValidationError()
        fun setLocalButtonText()
        fun setRemoteButtonText(buttonText: String)
        fun setLocalConfirmationTitle()
        fun setRemoteConfirmationTitle(supportedTitle: String)
        fun setLocalConfirmationSubTitle()
        fun setRemoteConfirmationSubTitle(supportedCopy: String)
        fun enablePostcodeCheckerFindButton()
        fun displayLoginText()
        fun postCodeSaved()
    }
}
