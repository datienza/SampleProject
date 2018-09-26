package com.farmdrop.customer.contracts.ordersummary

import uk.co.farmdrop.library.resolution.Resolution
import uk.co.farmdrop.library.ui.base.BaseListContract

interface FindAddressFragmentContract {

    interface View : BaseListContract.BaseListView<AddressData> {
        fun getResolution(): Resolution
        fun deselectAddress(position: Int)
        fun closeWithSelectedAddress(selectedAddress: AddressData)
    }
}
