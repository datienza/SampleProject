package com.farmdrop.customer.presenters.products

import com.farmdrop.customer.contracts.products.ProductsFragmentContract
import com.farmdrop.customer.utils.BasketRxBus
import uk.co.farmdrop.library.utils.Constants.ZERO

class ProductsFragmentPresenter(
    productsRepository: ProductsRepository,
    producersRepository: ProducersRepository,
    basketRxBus: BasketRxBus,
    currentDeliverySlot: CurrentDeliverySlot,
    name: String? = null,
    producerId: Int = ZERO
) : BaseProductsListPresenter<ProductsFragmentContract.View>(productsRepository, producersRepository, basketRxBus, currentDeliverySlot, name, producerId)
