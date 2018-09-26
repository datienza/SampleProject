package com.farmdrop.customer.contracts.products

import uk.co.farmdrop.library.ui.base.BaseListContract

interface BaseProductsListContract {

    interface View : BaseListContract.BaseListView<ProductData> {

        fun notifyItemChanged(itemPosition: Int)
    }
}
