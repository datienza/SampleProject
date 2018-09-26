package com.farmdrop.customer.contracts.basket

import android.support.annotation.ColorRes
import com.farmdrop.customer.contracts.common.BottomNavigationActivityContract

/**
 * Define contract methods between BasketActivity & BasketActivityPresenter
 */
interface BasketActivityContract {

    interface View : BottomNavigationActivityContract.View {
        fun showLogInSelectFragment()
        fun showOrderSummaryActivity()
        fun showCheckoutButton()
        fun hideCheckoutButton()
        fun setCheckoutButtonCost(basketCost: Float)
        fun showAgeCheckActivity()
        fun showUnavailableItems(count: Int)
        fun hideUnavailableItems()
        fun setCheckoutEnabled(enabled: Boolean)
        fun setCheckoutTextColor(@ColorRes colorRes: Int)
    }
}
