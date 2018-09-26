package com.farmdrop.customer.contracts.products

import uk.co.farmdrop.library.ui.base.BaseListContract

/**
 * Define contract methods between ProductsFragment & ProductsViewPresenter
 */
interface ProductsSearchFragmentContract {

    interface View : BaseListContract.BaseListView<ProductData> {
        fun showBaseListLayout()

        fun hideBaseListLayout()

        fun isBaseListLayoutVisible(): Boolean

        fun notifyItemChanged(position: Int)

        fun trackSearchedProductsAnalyticsEvent(query: String)
    }
}
