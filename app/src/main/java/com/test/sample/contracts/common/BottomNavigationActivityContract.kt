package com.farmdrop.customer.contracts.common

/**
 * Define contract methods between BottomNavigationActivity & BottomNavigationActivityPresenter
 */
interface BottomNavigationActivityContract {

    interface View : BaseSearchActivityContract.View {
        fun launchSavedIntent(destTabId: Int)
        fun launchDefaultIntent(destTabId: Int)
        fun finishActivity()
        fun updateBasketItem(amount: Float, nbItems: Int)
        fun displayBasketAsEmpty()
        fun displayBasketInitError()
    }
}
