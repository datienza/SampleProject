package com.farmdrop.customer.contracts.agecheck

import com.farmdrop.customer.data.model.order.BasketItemData
import uk.co.farmdrop.library.ui.base.BaseListContract

interface RemoveAlcoholFragmentContract {
    interface View : BaseListContract.BaseListView<BasketItemData> {
        fun onAlcoholRemoved()
    }
}