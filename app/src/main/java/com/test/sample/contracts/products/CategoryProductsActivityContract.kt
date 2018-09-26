package com.farmdrop.customer.contracts.products

import com.farmdrop.customer.contracts.common.BottomNavigationActivityContract

/**
 * Define contract methods between CategoryProductsActivity & CategoryProductsActivityPresenter
 */
interface CategoryProductsActivityContract {

    interface View : BottomNavigationActivityContract.View {
        fun setToolbarTitle(title: String?)
        fun getAllProductsTabTitle(): String
        fun initViewPagerAndTabs()
        fun addTab(title: String, name: String)
        fun finalizeInitViewPagerAndTabs()
        fun initProductsSingleFragment(name: String)
        fun trackViewedSubcategoryEvent(subcategory: String)
    }
}
