package com.farmdrop.customer.contracts.orderdetails

import com.farmdrop.customer.data.model.order.BasketItemData
import uk.co.farmdrop.library.ui.base.MvpView

class DeleteUnavailableItemsDialogFragmentContract {

    interface View : MvpView {
        fun showUnavailableItems(unavailableItems: List<BasketItemData>)
        fun unavailableItemsRemoved()
    }
}
