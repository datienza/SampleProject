package com.farmdrop.customer.presenters.products

import com.farmdrop.customer.contracts.products.ProductsViewContract
import com.farmdrop.customer.utils.BasketRxBus
import uk.co.farmdrop.library.utils.Constants.ZERO

class ProductsViewPresenter(
    productsRepository: ProductsRepository,
    producersRepository: ProducersRepository,
    basketRxBus: BasketRxBus,
    currentDeliverySlot: CurrentDeliverySlot,
    name: String? = null,
    producerId: Int = ZERO
) : BaseProductsListPresenter<ProductsViewContract.View>(productsRepository, producersRepository, basketRxBus, currentDeliverySlot, name, producerId) {

    override fun showEmptyResult() = mvpView.hideViewOnEmptyResult()
}
