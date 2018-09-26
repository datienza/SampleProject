package com.farmdrop.customer.contracts.basket

import com.farmdrop.customer.data.model.order.BasketItemContent
import uk.co.farmdrop.library.ui.base.BaseListContract

interface BasketItemsFragmentContract {

    interface View : BaseListContract.BaseListView<BasketItemContent> {
        fun showResultsView()
    }
}
