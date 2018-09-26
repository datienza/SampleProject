package com.farmdrop.customer.presenters.products

import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.products.BaseProductContract
import com.farmdrop.customer.data.model.MARKETING_TAG
import com.farmdrop.customer.data.model.TagData
import com.farmdrop.customer.data.model.events.BasketModifyEvent
import com.farmdrop.customer.data.repository.ProductsRepository
import com.farmdrop.customer.utils.BasketRxBus
import com.farmdrop.customer.utils.analytics.AnalyticsEvents
import com.farmdrop.customer.utils.analytics.AnalyticsEvents.FREQUENCY_PROPERTY
import com.farmdrop.customer.utils.analytics.AnalyticsEvents.ID_PROPERTY
import com.farmdrop.customer.utils.analytics.AnalyticsEvents.NAME_PROPERTY
import com.farmdrop.customer.utils.analytics.AnalyticsEvents.PAGE_PROPERTY
import com.farmdrop.customer.utils.analytics.AnalyticsEvents.PARENT_PRODUCT_NAME_PROPERTY
import com.farmdrop.customer.utils.analytics.AnalyticsEvents.PRICE_PROPERTY
import com.farmdrop.customer.utils.analytics.AnalyticsEvents.PRODUCER_ID_PROPERTY
import com.farmdrop.customer.utils.analytics.AnalyticsEvents.PRODUCER_PROPERTY
import com.farmdrop.customer.utils.analytics.AnalyticsEvents.PRODUCT_SOURCE_PROPERTY
import com.farmdrop.customer.utils.analytics.AnalyticsEvents.QUANTITY_PROPERTY
import com.farmdrop.customer.utils.analytics.AnalyticsEvents.VARIANT_PROPERTY
import com.farmdrop.customer.utils.analytics.ProductViewLocation
import com.segment.analytics.Properties
import uk.co.farmdrop.library.ui.base.BasePresenter
import uk.co.farmdrop.library.utils.Constants.ZERO

abstract class BaseProductPresenter<T : BaseProductContract.View>(
    private val currentBasket: CurrentBasket,
    protected val currentDeliverySlot: CurrentDeliverySlot,
    private val basketRxBus: BasketRxBus,
    private val productsRepository: ProductsRepository
) : BasePresenter<T>() {

    companion object {
        const val MIN_QUANTITY_DISPLAY_REMAINING_ITEMS = 10
        const val RADIO_BUTTON_TEXT_LENGTH_LIMIT = 6
        const val RADIO_BUTTON_MAX_ITEM = 3
        const val INDEX_0 = 0
        private const val NUM_ITEMS_ADDED_PER_CLICK = 1
    }

    open var productData: ProductData? = null

    open var productWithVariantsList: List<ProductData>? = null

    internal var selectedProductId: String? = null

    fun initProductData(productData: ProductData) {
        this.productData = productData
        this.productWithVariantsList = createProductWithVariantsList(productData)
    }

    open fun displayProductPicture(productData: ProductData) {
        val mainImage = productData.mainImageSrc
        if (mainImage != null) {
            mvpView.displayProductPicture(mainImage)
        } else {
            mvpView.displayDefaultProductPicture(R.drawable.ic_placeholder_image_selfie_carrot)
        }
    }

    open fun displayProductName(productData: ProductData) {
        productData.name?.let {
            mvpView.displayProductName(it)
        }
    }

    open fun displayProducerName(productData: ProductData) {
        productData.producerName?.let {
            mvpView.displayProducerName(it)
        }
    }

    abstract fun displayPriceForVariant(productVariant: ProductData)

    /**
     * Variants display logic:
     * - if only 1 variant, display a single text
     * - if variants nb > 1 && variants nb <= 3 AND that the text displayed in the radio button is small,
     *   display radio buttons for variants selection
     * - in other cases (more than 3 variants, long variants text...), display a drop down menu for variants selection
     */
    open fun displayVariants(variantsData: List<ProductData>?) {
        val size = variantsData?.size
        if (size != null) {
            if (size <= 1) {
                getVariant(variantsData, ZERO)?.let { productVariant ->
                    displaySingleTextVariant(productVariant)
                    displayPriceForVariant(productVariant)
                }
            } else {
                val variantsNames = createVariantsDisplayNamesWithUnits(variantsData)
                val longestVariantNameLength = findLongestStringLengthFromList(variantsNames)
                if (size <= RADIO_BUTTON_MAX_ITEM && longestVariantNameLength <= RADIO_BUTTON_TEXT_LENGTH_LIMIT) {
                    displayRadioButtonsForVariants(variantsData, variantsNames)
                } else {
                    displayDropDownMenuForVariants(variantsData, variantsNames)
                }
            }
        }
    }

    open fun displaySingleTextVariant(productVariant: ProductData) {
        mvpView.hideVariantsRadioGroup()
        mvpView.hideVariantsDropDown()
        displayVariantText(productVariant)
    }

    open fun displayRadioButtonsForVariants(productVariants: List<ProductData>, variantsDisplayNames: List<String?>) {
        mvpView.hideVariantsDropDown()

        mvpView.initVariantsRadioGroup()
        (0 until productVariants.size)
            .mapNotNull { it -> variantsDisplayNames[it] }
            .forEach { mvpView.addVariantRadioButton(it) }
        productData?.selectedVariantIndex?.let {
            mvpView.selectVariantRadioButton(it)
            displayPriceForVariant(productVariants[it])
        }
    }

    open fun displayDropDownMenuForVariants(productVariants: List<ProductData>, variantsDisplayNames: List<String?>) {
        mvpView.hideVariantsRadioGroup()

        mvpView.displayVariantsDropDown(variantsDisplayNames)
        productData?.selectedVariantIndex?.let {
            mvpView.selectVariantDropDown(it)
            displayPriceForVariant(productVariants[it])
        }
    }

    internal open fun displayVariantText(productVariant: ProductData) {
        val displayNameWithUnits = createSingleVariantDisplayNameWithUnits(productVariant)
        if (displayNameWithUnits != null) {
            mvpView.displayVariantSingleText(displayNameWithUnits)
        } else {
            mvpView.hideVariantSingleText()
        }
    }

    internal fun findLongestStringLengthFromList(list: List<String?>): Int {
        var size = 0
        list.forEach { if (it != null && it.length > size) size = it.length }
        return size
    }

    internal fun createVariantsDisplayNamesWithUnits(variantsData: List<ProductData>): List<String?> {
        val variantsDisplayNamesWithUnits = ArrayList<String?>(variantsData.size)
        variantsData.mapTo(variantsDisplayNamesWithUnits) { createSingleVariantDisplayNameWithUnits(it) }
        return variantsDisplayNamesWithUnits
    }

    internal fun createSingleVariantDisplayNameWithUnits(productVariant: ProductData): String? {
        val unit = productVariant.unit
        val value = productVariant.value
        val valueType = productVariant.valueType

        return if (unit != null && value != null && valueType != null) {
            mvpView.createVariantText(unit, value, valueType)
        } else if (value != null && valueType != null) {
            mvpView.createVariantText(value, valueType)
        } else {
            unit
        }
    }

    open fun displayMarketingTag(tagsData: List<TagData>?) {
        val firstMarketingTag = tagsData?.find { it.type == MARKETING_TAG }

        if (firstMarketingTag != null) {
            val firstMarketingTagName = firstMarketingTag.name
            if (firstMarketingTagName != null) {
                mvpView.showMarketingTag(firstMarketingTagName)
            } else {
                mvpView.hideMarketingTag()
            }
        } else {
            mvpView.hideMarketingTag()
        }
    }

    open fun onVariantSelected(index: Int) {
        if (productData?.selectedVariantIndex != index) {
            productsRepository.setSelectedProductVariantIndex(productData, index)
        }
        displayVariantForIndex(productData?.selectedVariantIndex)
    }

    open fun displayVariantForIndex(index: Int?) {
        val variant = getVariant(productWithVariantsList, index ?: 0)
        val graphQLId = variant?.graphQLId
        if (graphQLId != null) {
            selectedProductId = graphQLId
            displayPriceForVariant(variant)
            val countOfVariantsInBasket = currentBasket.quantityOfProductInBasket(graphQLId)
            displayItemsNumberRelatedUi(countOfVariantsInBasket)
        }
    }

    open fun createAddVariantEvent() = selectedProductId?.let { BasketModifyEvent.AddProduct(it, BasketModifyEvent.PLUS_ONE) }

    fun onAddItemToBasketClick(productViewLocation: ProductViewLocation?) {
        productData?.let { productData ->
            val basketItemQuantity = currentBasket.quantityOfProductInBasket(selectedProductId)
            if (basketItemQuantity < productData.quantity) {
                // if we're adding the last item
                if (basketItemQuantity == productData.quantity - 1) {
                    mvpView.showItemsSoldOut()
                    mvpView.disableAddToBasketMultipleItemButton()
                }

                productViewLocation?.let {
                    getVariant(selectedProductId)?.let { productVariant ->
                        mvpView.trackAddedProduct(createProductAnalyticsProperties(productViewLocation, productData, productVariant))
                    }
                }

                createAddVariantEvent()?.let {
                    basketRxBus.post(it)
                }
            }
        }
    }

    open fun createRemoveVariantEvent() = selectedProductId?.let { BasketModifyEvent.RemoveVariant(it) }

    fun onRemoveItemFromBasketClick(productViewLocation: ProductViewLocation?) {
        productData?.let { productData ->
            val basketItemQuantity = currentBasket.quantityOfProductInBasket(selectedProductId)
            if (basketItemQuantity == productData.quantity) {
                mvpView.hideItemsSoldOut()
                mvpView.enableAddToBasketMultipleItemButton()
            }

            productViewLocation?.let {
                getVariant(selectedProductId)?.let { productVariant ->
                    mvpView.trackRemovedProduct(createProductAnalyticsProperties(productViewLocation, productData, productVariant))
                }
            }

            createRemoveVariantEvent()?.let { removeVariantEvent ->
                basketRxBus.post(removeVariantEvent)
            }
        }
    }

    abstract fun displayItemsNumberRelatedUi(quantityToDisplay: Int)

    open fun displayAddToBasketUi(nbItems: Int) {
        when (nbItems) {
            0 -> {
                mvpView.showAddToBasketButton()
            }
            else -> {
                mvpView.hideAddToBasketButton()
                mvpView.updateBasketItemsNumber(nbItems)
            }
        }
    }

    private fun createProductAnalyticsProperties(productViewLocation: ProductViewLocation, productData: ProductData, productVariant: ProductData) =
        Properties().apply {
            putValue(PRODUCT_SOURCE_PROPERTY, productViewLocation.sourceProperty)
            putValue(PAGE_PROPERTY, productViewLocation.pageProperty)
            putValue(FREQUENCY_PROPERTY, AnalyticsEvents.NORMAL_VALUE)
            putValue(ID_PROPERTY, productData.graphQLId)
            putValue(PRODUCER_PROPERTY, productData.producerName)
            putValue(NAME_PROPERTY, productData.name)
            putValue(PRICE_PROPERTY, productVariant.price)
            putValue(QUANTITY_PROPERTY, NUM_ITEMS_ADDED_PER_CLICK)
            putValue(PRODUCER_ID_PROPERTY, productData.producerId)
            putValue(VARIANT_PROPERTY, createSingleVariantDisplayNameWithUnits(productVariant))
            putValue(PARENT_PRODUCT_NAME_PROPERTY, AnalyticsEvents.NONE_VALUE)
        }

    open fun displayQuantityRemaining(quantity: Int) {
        if (isRemainingItemsQuantityBelowMinimum(quantity)) {
            mvpView.showQuantityRemaining(quantity)
        } else {
            mvpView.hideQuantityRemaining()
        }
    }

    protected fun isRemainingItemsQuantityBelowMinimum(quantity: Int) = quantity < MIN_QUANTITY_DISPLAY_REMAINING_ITEMS

    protected fun getVariant(productVariants: List<ProductData>?, index: Int): ProductData? =
        if (productVariants?.isNotEmpty() == true && index >= ZERO && index < productVariants.size) {
            productVariants[index]
        } else {
            productData
        }

    private fun getVariant(graphQLId: String?): ProductData? =
        if (productData?.graphQLId == graphQLId) {
            productData
        } else {
            productData?.productVariants?.find { it.graphQLId == graphQLId }
        }

    protected fun getQuantityRemaining(quantity: Int) = quantity - currentBasket.quantityOfProductInBasket(productData?.graphQLId)

    internal fun displayQuantityOrMarketingTags(quantity: Int, tags: List<TagData>?) {
        val quantityRemaining = getQuantityRemaining(quantity)
        if (isRemainingItemsQuantityBelowMinimum(quantityRemaining)) {
            mvpView.showQuantityRemaining(quantityRemaining)
            mvpView.hideMarketingTag()
        } else {
            displayMarketingTag(tags)
            mvpView.hideQuantityRemaining()
        }
    }

    protected fun createProductWithVariantsList(productData: ProductData): List<ProductData> =
        ArrayList<ProductData>().apply {
            add(productData)
            productData.productVariants?.let {
                addAll(it)
            }
        }
}
