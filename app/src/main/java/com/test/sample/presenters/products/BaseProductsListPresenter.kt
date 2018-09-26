package com.farmdrop.customer.presenters.products

import com.farmdrop.customer.contracts.products.BaseProductsListContract
import com.farmdrop.customer.data.model.ProducerData
import com.farmdrop.customer.data.model.events.BasketUpdatedEvent
import com.farmdrop.customer.data.model.pagination.CursorData
import com.farmdrop.customer.presenters.common.BasePaginatedListPresenter
import com.farmdrop.customer.utils.BasketRxBus
import timber.log.Timber
import uk.co.farmdrop.library.exceptions.NoResultsException
import uk.co.farmdrop.library.utils.Constants.ONE_L
import uk.co.farmdrop.library.utils.Constants.ZERO

abstract class BaseProductsListPresenter<V : BaseProductsListContract.View>(
    private val productsRepository: ProductsRepository,
    private val producersRepository: ProducersRepository,
    private val basketRxBus: BasketRxBus,
    private val currentDeliverySlot: CurrentDeliverySlot,
    private val name: String? = null,
    private val producerId: Int = ZERO
) : BasePaginatedListPresenter<ProductData, V, CursorData<ProductData>>() {

    private val result = ArrayList<ProductData>()

    internal fun listenForBasketChangeEvents() {
        addDisposable(basketRxBus.basketUpdatedEvent.subscribe {
            if (it is BasketUpdatedEvent.ProductQuantityUpdated) {
                it.positionInList?.let { position ->
                    mvpView.notifyItemChanged(position)
                }
            }
        })
    }

    override fun loadData(forceReload: Boolean) {
        if (producerId != ZERO) {
            loadProducer(producerId)
        } else {
            loadProducts(forceReload)
        }
    }

    override fun loadData(forceLoad: Boolean, previousPaginatedData: CursorData<ProductData>?) {
        super.loadData(forceLoad, previousPaginatedData)
        if (producerId != ZERO) {
            loadProducer(producerId)
        } else {
            loadProducts(forceLoad, previousPaginatedData?.lastCursor)
        }
    }

    private fun loadProducts(forceLoad: Boolean, cursor: String? = null) {
        if (name != null) {
            addDisposable(productsRepository.getProductsGraphQL(forceLoad, cursor, name, currentDeliverySlot.getCurrentDropCycleId())
                    .take(ONE_L)
                    .subscribe({
                        onPaginatedDataLoaded(it)
                        hideSwipeRefreshingView(forceLoad)
                        appendDataToResult(it.data)
                        displayData(result)
                    }, {
                        displayError(it)
                    }))
        }
    }

    private fun loadProducer(producerId: Int) {
        addDisposable(producersRepository.getProducer(producerId)
                .take(ONE_L)
                .subscribe({
                    loadProductsForProducer(it)
                }, {
                    Timber.e(it)
                }))
    }

    private fun loadProductsForProducer(producerData: ProducerData) {
        addDisposable(productsRepository.searchProductsByProducer(producerData, currentDeliverySlot.getCurrentDropCycleId())
                .take(ONE_L)
                .subscribe({
                    onPaginatedDataLoaded(it)
                    hideSwipeRefreshingView(true)
                    appendDataToResult(it.data)
                    displayData(result)
                }, {
                    displayError(it)
                }))
    }

    private fun appendDataToResult(productsData: List<ProductData>?) {
        if (productsData != null) {
            result.addAll(productsData)
        }
    }

    private fun displayError(throwable: Throwable) {
        mvpView.hideProgressBar()
        hideSwipeRefreshingView(true)
        if (throwable is NoResultsException) {
            showEmptyResult()
        }
    }
}
