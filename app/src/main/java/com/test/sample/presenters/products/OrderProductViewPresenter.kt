package com.farmdrop.customer.presenters.products

import com.farmdrop.customer.contracts.products.OrderProductViewContract
import com.farmdrop.customer.data.model.events.BasketModifyEvent
import com.farmdrop.customer.utils.BasketRxBus
import com.farmdrop.customer.utils.constants.OrderConstants
import uk.co.farmdrop.library.utils.Constants.ZERO

class OrderProductViewPresenter(
    currentBasket: CurrentBasket,
    currentDeliverySlot: CurrentDeliverySlot,
    basketRxBus: BasketRxBus,
    productsRepository: ProductsRepository
) : BaseProductPresenter<OrderProductViewContract.View>(currentBasket, currentDeliverySlot, basketRxBus, productsRepository) {

    private var positionInList = ZERO

    fun bind(productData: ProductData, quantityInOrder: Int, orderProgress: String?, listPosition: Int) {
        positionInList = listPosition
        initProductData(productData)
        selectedProductId = productData.graphQLId
        displayProductPicture(productData)
        displayProductName(productData)
        displayVariantText(productData)
        displayPriceForVariant(productData)
        displayQuantityInOrderProductImageOverlay(quantityInOrder)
        checkAvailability(productData.quantity)
        checkOrderProgress(orderProgress, quantityInOrder)
    }

    override fun displayVariantText(productVariant: ProductData) {
        createSingleVariantDisplayNameWithUnits(productVariant)?.let {
            mvpView.displayVariantSingleText(it)
        } ?: mvpView.hideVariantSingleText()
    }

    internal fun checkOrderProgress(orderProgress: String?, quantityInOrder: Int) {
        orderProgress?.let {
            // TODO: When Order is Deliverd or cancelled, we should add items to current basket.
            // On the other hand, we still need to add the edit mode of an open or harvested order.
            when (orderProgress) {
                OrderConstants.ORDER_PROGRESS_DELIVERED,
                OrderConstants.ORDER_PROGRESS_CANCELLED -> displayAddToBasketUi(quantityInOrder)
                else -> mvpView.hideAddRemoveButtons()
            }
        }
    }

    internal fun checkAvailability(quantity: Int) {
        if (quantity <= ZERO) {
            mvpView.showItemAsUnavailable()
        }
    }

    override fun displayAddToBasketUi(nbItems: Int) {
        super.displayAddToBasketUi(nbItems)
        when (nbItems) {
            ZERO -> mvpView.hideBasketMultipleItemsLayout()
            else -> mvpView.showBasketMultipleItemsLayout()
        }
    }

    internal fun displayQuantityInOrderProductImageOverlay(quantity: Int) {
        // TODO: We'd need to revisit and display the remaining quantity correctly,
        // once the placed order basket information is available to be modified
        val quantityRemaining = getQuantityRemaining(quantity)
        mvpView.showProductImageOverlay()
        mvpView.updateProductImageOverlayItemsNumber(quantityRemaining)
    }

    override fun displayItemsNumberRelatedUi(quantityToDisplay: Int) {
        displayAddToBasketUi(quantityToDisplay)
    }

    override fun createAddVariantEvent() = selectedProductId?.let { BasketModifyEvent.AddProduct(it, BasketModifyEvent.PLUS_ONE, positionInList) }

    override fun createRemoveVariantEvent() = selectedProductId?.let { BasketModifyEvent.RemoveVariant(it, positionInList) }

    override fun displayPriceForVariant(productVariant: ProductData) {
        var pricePerUnitDisplayed = false
        if (productVariant.displayPricePerUnit) {
            val pricePerUnit = productVariant.pricePerUnit
            if (pricePerUnit != null) {
                mvpView.displayPricePerUnit(pricePerUnit)
                pricePerUnitDisplayed = true
            }
        }

        if (productVariant.salePricePence > ZERO) {
            mvpView.displaySalePrice(productVariant.salePrice)
            if (!pricePerUnitDisplayed) {
                mvpView.displayOldPrice(productVariant.price)
            }
        } else {
            mvpView.displayPrice(productVariant.price)
            if (!pricePerUnitDisplayed) {
                mvpView.hideSecondaryPrice()
            }
        }
    }
}
