package com.farmdrop.customer.presenters.products

    import com.farmdrop.customer.contracts.products.ProductsActivityContract
    import com.farmdrop.customer.managers.BasketManager
import com.farmdrop.customer.presenters.common.BottomNavigationActivityPresenter
import com.farmdrop.customer.ui.products.ProductsActivity
import com.farmdrop.customer.utils.BasketRxBus
import com.farmdrop.customer.utils.analytics.AnalyticsEvents
import io.reactivex.Scheduler

/**
 * Handle ProductsActivity logic.
 * Created by datienza on 14/08/2017.
 */
class ProductsActivityPresenter(
    bottomNavigationActivityTabsManager: BaseBottomNavigationActivityTabsManager,
    basketRxBus: BasketRxBus,
    basketManager: BasketManager,
    uiScheduler: Scheduler
) : BottomNavigationActivityPresenter<ProductsActivityContract.View>(bottomNavigationActivityTabsManager, basketRxBus, basketManager, uiScheduler) {

    fun onTabSelected(position: Int) {
        val title = if (position == ProductsActivity.PRODUCTS_TAB) AnalyticsEvents.PRODUCTS_VALUE else AnalyticsEvents.PRODUCERS_VALUE
        mvpView.trackTabSelectEvent(title)
    }
}
