package com.farmdrop.customer.presenters.products

import com.farmdrop.customer.contracts.products.ProductsSearchActivityContract
import com.farmdrop.customer.managers.BasketManager
import com.farmdrop.customer.presenters.common.BottomNavigationActivityPresenter
import com.farmdrop.customer.utils.BasketRxBus
import io.reactivex.Scheduler

/**
 * Handle ProductsSearchActivity logic.
 */
class ProductsSearchActivityPresenter(
    bottomNavigationActivityTabsManager: BaseBottomNavigationActivityTabsManager,
    basketRxBus: BasketRxBus,
    basketManager: BasketManager,
    uiScheduler: Scheduler
) : BottomNavigationActivityPresenter<ProductsSearchActivityContract.View>(bottomNavigationActivityTabsManager, basketRxBus, basketManager, uiScheduler) {

    fun onTouchUp(): Boolean {
        if (mvpView.isSearchInInitialState()) {
            mvpView.finishActivity()
            return true
        }
        return false
    }
}
