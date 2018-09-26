package com.farmdrop.customer.contracts.unavailableitems

import uk.co.farmdrop.library.ui.base.MvpView

interface UnavailableItemsFragmentContract {
    interface View : MvpView {
        fun populateList(unavailableItems: List<UnavailableItemsContent.Item>)
        fun unavailableItemsRemoved()
        fun removeItemFromList(unavailableItem: UnavailableItemsContent)
    }
}
