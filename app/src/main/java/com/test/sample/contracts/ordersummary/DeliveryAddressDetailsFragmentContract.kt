package com.farmdrop.customer.contracts.ordersummary

import android.support.annotation.StringRes
import uk.co.farmdrop.library.resolution.Resolution
import uk.co.farmdrop.library.ui.base.MvpView

interface DeliveryAddressDetailsFragmentContract {
    interface View : MvpView {
        fun onDetailsEnteredSuccess()
        fun onFirstNameError()
        fun onLastNameError()
        fun onMobileNumberLengthError()
        fun onPostcodeError()
        fun onPostcodeFormatError()
        fun onCityError()
        fun onAddressLine1Error()
        fun onMobileNumberEmpty()
        fun showPostcode(postCode: String)
        fun setDeliveryAddressDetails(selectedAddress: AddressData)
        fun showHiddenInputLayouts()
        fun showEnterAddressOptions()
        fun hideEnterAddressOptions()
        fun noDeliveryToPostcode()
        fun postcodeValid()
        fun getResolution(): Resolution
        fun showAddress1(address1: String)
        fun showAddress2(address2: String)
        fun showCity(city: String)
        fun showFirstName(firstName: String)
        fun showLastName(lastName: String)
        fun showMobileNumber(phoneNumber: String): Any
        fun clearInputFieldsErrors()
        fun setFindAddressButtonText(@StringRes textResId: Int)

        fun setRemoteGDPRText(text: String)
        fun setLocalGDPRText()

        fun hideSendAddressButton()
        fun showSendAddressButton()
        fun setSendAddressButtonSaveText()
        fun showSendAddressInProgress()
        fun isSendAddressButtonVisible(): Boolean
    }
}
