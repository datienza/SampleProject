package com.farmdrop.customer.presenters.products

import com.farmdrop.customer.contracts.products.ProductDetailsActivityContract
import com.farmdrop.customer.data.model.ProducerData
import com.farmdrop.customer.data.model.events.BasketUpdatedEvent
import com.farmdrop.customer.utils.BasketRxBus
import io.reactivex.Scheduler
import timber.log.Timber
import uk.co.farmdrop.library.utils.Constants.INVALID_ID
import uk.co.farmdrop.library.utils.Constants.ONE_L
import uk.co.farmdrop.library.utils.Constants.ZERO

class ProductDetailsActivityPresenter(
    private val productsRepository: ProductsRepository,
    internal var productId: Int,
    private val graphQLProductId: String?,
    private val scheduler: Scheduler,
    private val producersRepository: ProducersRepository,
    currentBasket: CurrentBasket,
    currentDeliverySlot: CurrentDeliverySlot,
    private val basketRxBus: BasketRxBus
) : BaseProductPresenter<ProductDetailsActivityContract.View>(currentBasket, currentDeliverySlot, basketRxBus, productsRepository) {

    internal var productDetailsDisplayed = false

    fun loadData() {
        val productFlowable = when {
            productId != INVALID_ID -> productsRepository.getProductGraphQL(productId, currentDeliverySlot.getCurrentDropCycleId())
            graphQLProductId != null -> productsRepository.getProductGraphQL(graphQLProductId, currentDeliverySlot.getCurrentDropCycleId())
            else -> null
        }

        if (productFlowable != null) {
            addDisposable(productFlowable
                .take(ONE_L)
                .subscribeOn(scheduler)
                .subscribe({ loadedProductData ->
                    loadedProductData?.let {
                        initProductData(it)
                        displayProductPicture(it)
                        onVariantSelected(it.selectedVariantIndex)
                        displayProductName(it)
                        displayProducerName(it)
                        loadProducerDetails(it)
                        displayDescription(it)
                        displayVariants(productWithVariantsList)
                        displayIngredients(it)
                        displayProperties(it)
                        displayIntroParagraph(it)
                        displayOrigin(it)
                        displayServes(it)
                        displayShelfLife(it)
                        displayAwards(it)
                        displayCookingInstructions(it)
                        displayStorageInformation(it)
                        displayNutritionalInformation(it)
                        displayMeetProducerTopLine()
                        displayQuantityOrMarketingTags(it.quantity, it.tags)
                    }
                }, {
                    Timber.e(it)
                }))
        }
    }

    internal fun loadProducerDetails(productData: ProductData) {
        addDisposable(producersRepository.getProducer(productData.producerId)
            .subscribe(::onProducerLoaded) {
                Timber.e(it)
            })
    }

    fun displayIntroParagraph(productData: ProductData) {
        productData.introParagraph?.let {
            if (!it.isEmpty()) {
                mvpView.displayProductIntroParagraph(it)
            }
        }
    }

    fun displayDescription(productData: ProductData) {
        productData.description?.let {
            if (!it.isEmpty()) {
                mvpView.displayProductDescription(it)
            }
        }
    }

    fun displayOrigin(productData: ProductData) {
        productData.origin?.let {
            if (!it.isEmpty()) {
                mvpView.displayOrigin(it)
            }
        }
    }

    fun displayServes(productData: ProductData) {
        productData.serves?.let {
            if (!it.isEmpty()) {
                mvpView.displayServes(it)
            }
        }
    }

    fun displayProperties(productData: ProductData) {
        val properties = productData.getProperties()
        if (properties != null && properties.isNotEmpty()) {
            mvpView.showPropertiesLayout()
            properties.forEach { property ->
                mvpView.addProperty(property)
            }
        } else {
            mvpView.hidePropertiesLayout()
        }
    }

    fun displayAwards(productData: ProductData) {
        if (productData.awards.isNotEmpty()) {
            mvpView.displayAwards(productData.awards)
        }
    }

    fun displayShelfLife(productData: ProductData) {
        productData.shelfLifeDays?.let {
            if (!it.isEmpty()) {
                mvpView.displayShelfLife(it)
            }
        }
    }

    fun displayIngredients(productData: ProductData) {
        productData.ingredients?.let {
            if (!it.isEmpty()) {
                mvpView.displayIngredients()
                productDetailsDisplayed = true
            }
        }
    }

    fun displayCookingInstructions(productData: ProductData) {
        productData.cookingInstructions?.let {
            if (!it.isEmpty()) {
                mvpView.displayCookingInstructions()
                productDetailsDisplayed = true
            }
        }
    }

    fun displayStorageInformation(productData: ProductData) {
        productData.storageInformation?.let {
            if (!it.isEmpty()) {
                mvpView.displayStorageInformation()
                productDetailsDisplayed = true
            }
        }
    }

    fun displayNutritionalInformation(productData: ProductData) {
        productData.nutrition?.let {
            mvpView.displayNutritionalInformation()
            productDetailsDisplayed = true
        }
    }

    override fun displayItemsNumberRelatedUi(quantityToDisplay: Int) {
        displayAddToBasketUi(quantityToDisplay)
    }

    private fun onProducerLoaded(producerData: ProducerData) {
        mvpView.displayProducerDetailsView(producerData)
        mvpView.displayProducerProductsCarousel(producerData.id)
    }

    internal fun displayMeetProducerTopLine() {
        if (productDetailsDisplayed) {
            mvpView.hideMeetProducerTopLine()
        } else {
            mvpView.showMeetProducerTopLine()
        }
    }

    override fun displayRadioButtonsForVariants(productVariants: List<ProductData>, variantsDisplayNames: List<String?>) {
        super.displayRadioButtonsForVariants(productVariants, variantsDisplayNames)
        productData?.selectedVariantIndex?.let {
            displaySelectedVariantText(it, productVariants)
        }
    }

    override fun displayDropDownMenuForVariants(productVariants: List<ProductData>, variantsDisplayNames: List<String?>) {
        super.displayDropDownMenuForVariants(productVariants, variantsDisplayNames)
        productData?.selectedVariantIndex?.let {
            displaySelectedVariantText(it, productVariants)
        }
    }

    override fun onVariantSelected(index: Int) {
        super.onVariantSelected(index)
        getVariant(productWithVariantsList, index)?.let {
            displayVariantText(it)
        }
    }

    private fun displaySelectedVariantText(index: Int, productVariant: List<ProductData>) {
        if (index <= productVariant.lastIndex) {
            displayVariantText(productVariant[index])
        }
    }

    internal fun listenForBasketChangeEvents() {
        addDisposable(basketRxBus.basketUpdatedEvent.subscribe {
            if (it is BasketUpdatedEvent.ProductQuantityUpdated && selectedProductId == it.productId) {
                displayItemsNumberRelatedUi(it.totalCount)
                productData?.let { productData ->
                    displayQuantityOrMarketingTags(productData.quantity, productData.tags)
                }
            }
        })
    }

    override fun displayVariantText(productVariant: ProductData) {
        val displayNameWithUnits = createSingleVariantDisplayNameWithUnits(productVariant)

        var variantText: String? = null
        if (displayNameWithUnits != null && productVariant.displayPricePerUnit) {
            val pricePerUnit = productVariant.pricePerUnit
            if (pricePerUnit != null) {
                variantText = mvpView.formatVariantDisplayNameWithUnitWithPricePerUnit(displayNameWithUnits, pricePerUnit)
            }
        }
        if (variantText == null) {
            variantText = displayNameWithUnits
        }
        if (variantText != null) {
            mvpView.displayVariantSingleText(variantText)
        } else {
            mvpView.hideVariantSingleText()
        }
    }

    override fun displayPriceForVariant(productVariant: ProductData) {
        if (productVariant.salePricePence > ZERO) {
            mvpView.displayDiscountedPrice(productVariant.salePrice, productVariant.price)
        } else {
            mvpView.displayPrice(productVariant.price)
        }
    }
}
