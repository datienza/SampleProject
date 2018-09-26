package com.farmdrop.customer.presenters.common

import com.farmdrop.customer.contracts.common.BottomNavigationActivityContract
import com.farmdrop.customer.data.model.events.BasketUpdatedEvent
import com.farmdrop.customer.managers.BasketManager
import com.farmdrop.customer.penceToPounds
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import uk.co.farmdrop.library.utils.Constants.ZERO

open class BottomNavigationActivityPresenter<V : BottomNavigationActivityContract.View>(
    private val bottomNavigationActivityTabsManager: BaseBottomNavigationActivityTabsManager,
    private val basketRxBus: BasketRxBus,
    protected val basketManager: BasketManager,
    private val uiScheduler: Scheduler
) : BaseSearchActivityPresenter<V>() {

    private var basketUpdatedEventDisposable: Disposable? = null

    fun initBasket() {
        basketManager.initBasket().observeOn(uiScheduler).subscribe({ }, {
            mvpView.displayBasketInitError()
        })
    }

    fun navigateToTab(currentTabId: Int, destTabId: Int) {
        if (currentTabId != destTabId) {
            if (bottomNavigationActivityTabsManager.savedIntentExist(destTabId)) {
                mvpView.launchSavedIntent(destTabId)
            } else {
                mvpView.launchDefaultIntent(destTabId)
            }
        } else if (bottomNavigationActivityTabsManager.savedIntentExist(destTabId)) {
            bottomNavigationActivityTabsManager.removeIntent(destTabId)
            mvpView.finishActivity()
            mvpView.launchDefaultIntent(destTabId)
        }
    }

    open fun navigateBack() {
        bottomNavigationActivityTabsManager.removeAllIntents()
    }

    fun showCurrentBasketStatus() {
        val basketCostPence = basketManager.getBasketPenceCost()
        val itemCount = basketManager.getBasketItemCount()
        handleBasketSyncedEvent(basketCostPence, itemCount)
    }

    fun listenForBasketUpdateEvents() {
        basketUpdatedEventDisposable = basketRxBus.basketUpdatedEvent.observeOn(uiScheduler).subscribe {
            when (it) {
                is BasketUpdatedEvent.BasketSynced -> {
                    val basketCostPence = basketManager.getBasketPenceCost()
                    val itemCount = basketManager.getBasketItemCount()
                    handleBasketSyncedEvent(basketCostPence, itemCount)
                }
            }
        }
    }

    fun clearBasketUpdateEventDisposable() = basketUpdatedEventDisposable?.dispose()

    protected open fun handleBasketSyncedEvent(basketCostPence: Int, itemCount: Int) {
        updateBasketItemTab(basketCostPence, itemCount)
    }

    private fun updateBasketItemTab(basketCostPence: Int, itemCount: Int) {
        if (itemCount == ZERO) {
            mvpView?.displayBasketAsEmpty()
        } else {
            val basketCost = penceToPounds(basketCostPence)
            mvpView?.updateBasketItem(basketCost, itemCount)
        }
    }
}
