package com.farmdrop.customer.contracts.alternativeitems

import uk.co.farmdrop.library.ui.base.MvpView

interface AlternativeItemsFragmentContract {
    interface View : MvpView {
        fun addUnavailableItem(unavailableItemContent: AlternativeItemsContent.Unavailable)
        fun addAlternativeItems(alternativeItemsContentList: List<AlternativeItemsContent.Item>)
        fun setListAdapter()
        fun itemFromBasketSwapped()
        fun unavailableItemRemoved()
    }
}
