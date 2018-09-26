package com.farmdrop.customer.contracts.ordersummary

import android.support.annotation.StringRes
import uk.co.farmdrop.library.ui.base.MvpView

interface OrderSummaryActivityContract {

    interface View : MvpView {

        fun isDeliveryAddressDetailsFragmentVisible(): Boolean

        fun setToDeliveryAddressFragment()

        fun setToLeaveSafeFragment()

        fun setToDriverInstructionsFragment()

        fun setTitleText(@StringRes stringId: Int)

        fun setToolbarIconAsBack()

        fun setToolbarIconAsCross()

        fun finishActivity()

        fun getCurrentFragment()

        fun popBackStack()

        fun refreshDeliveryAddress()

        fun setAddressSelected(selectedAddress: AddressData)

        fun showHiddenInputsLayouts()

        fun hideEnterAddressOptions()

        fun setLeaveSafeChecked(checked: Boolean)

        fun checkAndSendDriverInstructionsUpdate()

        fun checkAndShowDriverInstructionsPreview(driverInstructions: String)
    }
}
