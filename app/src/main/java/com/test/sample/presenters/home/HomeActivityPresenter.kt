package com.farmdrop.customer.presenters.home

import com.farmdrop.customer.contracts.home.HomeActivityContract
import com.farmdrop.customer.contracts.session.UserSession
import com.farmdrop.customer.data.wrapper.UserSessionHandler
import com.farmdrop.customer.managers.BasketManager
import com.farmdrop.customer.presenters.common.BottomNavigationActivityPresenter
import io.reactivex.Scheduler
import timber.log.Timber
import uk.co.farmdrop.library.utils.Constants.ZERO

/**
 * Handle HomeActivity logic.
 */

class HomeActivityPresenter(
    bottomNavigationActivityTabsManager: BaseBottomNavigationActivityTabsManager,
    private val ordersRepository: OrdersRepository,
    basketRxBus: BasketRxBus,
    basketManager: BasketManager,
    private val userSessionHandler: UserSessionHandler,
    uiScheduler: Scheduler
) : BottomNavigationActivityPresenter<HomeActivityContract.View>(bottomNavigationActivityTabsManager, basketRxBus, basketManager, uiScheduler), UserSession.Login by userSessionHandler, UserSession.Logout by userSessionHandler, UserSession.Reinitialise by userSessionHandler {

    fun onLogInOutPressed() {
        if (isUserLoggedIn()) {
            finishUserSession()
            mvpView.trackUserLogOutAnalyticsEvent()
            mvpView.showLogInButtonText()
            showDeliverySlotAndHideOrderLayout()
        } else {
            mvpView.openSessionActivity()
        }
    }

    fun updateUiAccordingToUserLoginState() {
        if (isUserLoggedIn()) {
            mvpView.showLogOutButtonText()
            updateUpcomingOrder()
        } else {
            mvpView.showLogInButtonText()
            showDeliverySlotAndHideOrderLayout()
        }
    }

    internal fun updateUpcomingOrder() {
        addDisposable(ordersRepository.getOrders().subscribe({ paginatedOrderData ->
            paginatedOrderData.data?.let { orders ->
                val inProgressOrders = orders.filter { it.isInProgress() }.sortedWith(compareBy { it.deliveryDate })
                if (inProgressOrders.isNotEmpty() && inProgressOrders[ZERO].isInProgress()) {
                    mvpView.hideDeliverySlotView()
                    mvpView.showOrder(inProgressOrders[ZERO])
                } else {
                    showDeliverySlotAndHideOrderLayout()
                }
            }
        }, {
            Timber.e(it)
            showDeliverySlotAndHideOrderLayout()
        }))
    }

    internal fun showDeliverySlotAndHideOrderLayout() {
        mvpView.showDeliverySlotView()
        mvpView.hideOrderLayout()
    }
}
