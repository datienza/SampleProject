package com.farmdrop.customer.contracts.products

import com.farmdrop.customer.contracts.common.BottomNavigationActivityContract

/**
 * Define contract methods between ProductsActivity & ProductsActivityPresenter
 */
interface ProductsActivityContract {

    interface View : BottomNavigationActivityContract.View {
        fun trackTabSelectEvent(title: String)
    }
}
