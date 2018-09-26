package com.farmdrop.customer.presenters.products

import com.farmdrop.customer.contracts.products.ProductsSearchFragmentContract
import com.farmdrop.customer.data.model.events.BasketUpdatedEvent
import com.farmdrop.customer.data.model.pagination.CursorData
import com.farmdrop.customer.utils.BasketRxBus
import uk.co.farmdrop.library.exceptions.NoResultsException
import uk.co.farmdrop.library.ui.base.BaseListPresenter
import uk.co.farmdrop.library.utils.Constants.ONE_L

private const val MINIMUM_LENGTH_QUERY_NON_SUBMITTED = 3

class ProductsSearchFragmentPresenter(private val productsRepository: ProductsRepository, private val basketRxBus: BasketRxBus, private val currentDeliverySlot: CurrentDeliverySlot) :
    BaseListPresenter<ProductData, ProductsSearchFragmentContract.View>() {

    private var query: String? = null

    private val results = ArrayList<ProductData>()

    fun performSearch(query: String?, querySubmitted: Boolean) {
        query?.let {
            if (!it.isEmpty()) {
                if ((querySubmitted || it.length >= MINIMUM_LENGTH_QUERY_NON_SUBMITTED)) {
                    mvpView.trackSearchedProductsAnalyticsEvent(query)
                    if (!mvpView.isBaseListLayoutVisible()) {
                        mvpView.showBaseListLayout()
                    }
                    destroyAllDisposables()
                    listenForBasketChangeEvents()
                    this.query = query
                    results.clear()
                    showLoadingOnStart()
                    loadData(true)
                }
            } else {
                clearSearch()
            }
        }
    }

    fun clearSearch() {
        destroyAllDisposables()
        results.clear()
        mvpView.hideBaseListLayout()
        mvpView.hideEmptySearchView()
        mvpView.hideEmptyResultView()
        mvpView.hideList()
        mvpView.hideProgressBar()
    }

    override fun loadData(forceLoad: Boolean) {
        query?.let { query ->
            addDisposable(productsRepository.searchProductsGraphQL(query, currentDeliverySlot.getCurrentDropCycleId())
                .take(ONE_L)
                .subscribe({ products: CursorData<ProductData> ->
                    hideSwipeRefreshingView(forceLoad)
                    products.data?.let {
                        results.addAll(it)
                    }
                    displaySearchResult(results)
                }, {
                    displayError(it)
                }))
        }
    }

    internal fun listenForBasketChangeEvents() {
        addDisposable(basketRxBus.basketUpdatedEvent.subscribe {
            if (it is BasketUpdatedEvent.ProductQuantityUpdated) {
                it.positionInList?.let { position ->
                    mvpView.notifyItemChanged(position)
                }
            }
        })
    }

    private fun displayError(throwable: Throwable) {
        mvpView.hideProgressBar()
        hideSwipeRefreshingView(true)
        if (throwable is NoResultsException) {
            showEmptySearchResult()
        }
    }
}
