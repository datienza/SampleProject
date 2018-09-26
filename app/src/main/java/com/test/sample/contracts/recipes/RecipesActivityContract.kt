package com.farmdrop.customer.contracts.recipes

import com.farmdrop.customer.contracts.common.BottomNavigationActivityContract

/**
 * Define contract methods between RecipesActivity & RecipesActivityPresenter
 */
interface RecipesActivityContract {

    interface View : BottomNavigationActivityContract.View {
        fun displayLogMessage()
    }
}
