package com.farmdrop.customer.presenters.products

import com.farmdrop.customer.contracts.products.ProductViewContract
import com.farmdrop.customer.data.model.events.BasketModifyEvent
import com.farmdrop.customer.utils.BasketRxBus
import com.farmdrop.customer.utils.analytics.ProductViewLocation
import uk.co.farmdrop.library.utils.Constants.ZERO

/**
 * Handle all the logic to display the product tile content.
 * This class inherits the BasePresenter, but the compositeDisposable is overwritten with one supplied from a RecyclerViewAdaptor lifecycle instead of the usual
 * onResume / onPause from the activities / fragments.
 */
class ProductViewPresenter(
    currentBasket: CurrentBasket,
    currentDeliverySlot: CurrentDeliverySlot,
    basketRxBus: BasketRxBus,
    productsRepository: ProductsRepository
) : BaseProductPresenter<ProductViewContract.View>(currentBasket, currentDeliverySlot, basketRxBus, productsRepository) {

    private var positionInList = 0

    fun bind(productData: ProductData, productViewLocation: ProductViewLocation?, position: Int) {
        initProductData(productData)
        positionInList = position
        displayProductPicture(productData)
        displayProductName(productData)
        productViewLocation?.let {
            if (productViewLocation.location == ProductViewLocation.PRODUCT_SUBTAXON) {
                displayProducerName(productData)
            }
        }
        displayVariants(productWithVariantsList)
        displayVariantForIndex(this.productData?.selectedVariantIndex)
        displayQuantityOrMarketingTags(productData.quantity, productData.tags)
    }

    override fun displayAddToBasketUi(nbItems: Int) {
        super.displayAddToBasketUi(nbItems)
        when (nbItems) {
            0 -> {
                mvpView.hideBasketMultipleItemsLayout()
            }
            else -> {
                mvpView.showBasketMultipleItemsLayout()
            }
        }
    }

    internal fun displayBasketItemsNumberProductImageOverlay(nbItems: Int) {
        when (nbItems) {
            0 -> {
                mvpView.hideProductImageOverlay()
            }
            else -> {
                mvpView.showProductImageOverlay()
                mvpView.updateProductImageOverlayItemsNumber(nbItems)
            }
        }
    }

    override fun displayRadioButtonsForVariants(productVariants: List<ProductData>, variantsDisplayNames: List<String?>) {
        super.displayRadioButtonsForVariants(productVariants, variantsDisplayNames)
        mvpView.hideVariantSingleText()
    }

    override fun displayDropDownMenuForVariants(productVariants: List<ProductData>, variantsDisplayNames: List<String?>) {
        super.displayDropDownMenuForVariants(productVariants, variantsDisplayNames)
        mvpView.hideVariantSingleText()
    }

    override fun displayItemsNumberRelatedUi(quantityToDisplay: Int) {
        displayAddToBasketUi(quantityToDisplay)
        displayBasketItemsNumberProductImageOverlay(quantityToDisplay)
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
