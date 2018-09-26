package com.farmdrop.customer.contracts.products

import com.farmdrop.customer.contracts.common.BottomNavigationActivityContract

/**
 * Define contract methods between ProductsSearchActivity & ProductsSearchActivityPresenter
 */
interface ProductsSearchActivityContract {

    interface View : BottomNavigationActivityContract.View {
        fun isSearchInInitialState(): Boolean
    }
}
