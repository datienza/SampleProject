package com.farmdrop.customer.ui.products

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.widget.NestedScrollView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.animation.AlphaAnimation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.products.ProductDetailsActivityContract
import com.farmdrop.customer.data.model.MediaData
import com.farmdrop.customer.data.model.ProducerData
import com.farmdrop.customer.data.model.TagData
import com.farmdrop.customer.di.app.AppModule
import com.farmdrop.customer.extensions.EMPTY
import com.farmdrop.customer.extensions.getScreenDimensionsInPixels
import com.farmdrop.customer.extensions.toSpanned
import com.farmdrop.customer.presenters.products.ProductDetailsActivityPresenter
import com.farmdrop.customer.ui.common.BaseActivity
import com.farmdrop.customer.ui.producers.ProducerDetailsActivity
import com.farmdrop.customer.utils.analytics.AnalyticsEvents
import com.farmdrop.customer.utils.analytics.AnalyticsHelper
import com.farmdrop.customer.utils.analytics.ProductViewLocation
import com.farmdrop.customer.utils.analytics.ProductViewLocationFactory
import com.farmdrop.customer.utils.constants.COOKING_INSTRUCTIONS
import com.farmdrop.customer.utils.constants.Constants
import com.farmdrop.customer.utils.constants.INGREDIENTS
import com.farmdrop.customer.utils.constants.NUTRITIONAL_INFO
import com.farmdrop.customer.utils.constants.ProductMoreInfoTypes
import com.farmdrop.customer.utils.constants.STORAGE_INFO
import com.segment.analytics.Properties
import kotlinx.android.synthetic.main.activity_product_details.addToBasketBottomView
import kotlinx.android.synthetic.main.activity_product_details.addToBasketTopView
import kotlinx.android.synthetic.main.activity_product_details.appBarLayout
import kotlinx.android.synthetic.main.activity_product_details.collapsingToolbar
import kotlinx.android.synthetic.main.activity_product_details.emptyDetailsText
import kotlinx.android.synthetic.main.activity_product_details.linearlayoutTitle
import kotlinx.android.synthetic.main.activity_product_details.marketingTagTextView
import kotlinx.android.synthetic.main.activity_product_details.meetProducerTextView
import kotlinx.android.synthetic.main.activity_product_details.meetProducerTopLineView
import kotlinx.android.synthetic.main.activity_product_details.moreFromProducerTextView
import kotlinx.android.synthetic.main.activity_product_details.moreProductsCarousel
import kotlinx.android.synthetic.main.activity_product_details.nestedScrollView
import kotlinx.android.synthetic.main.activity_product_details.producerTitleText
import kotlinx.android.synthetic.main.activity_product_details.productAwardsLayout
import kotlinx.android.synthetic.main.activity_product_details.productAwardsText
import kotlinx.android.synthetic.main.activity_product_details.productCookingInstructionsText
import kotlinx.android.synthetic.main.activity_product_details.productDescriptionMarkdown
import kotlinx.android.synthetic.main.activity_product_details.productImage
import kotlinx.android.synthetic.main.activity_product_details.productIngredientsText
import kotlinx.android.synthetic.main.activity_product_details.productIntroParagraphMarkdown
import kotlinx.android.synthetic.main.activity_product_details.productNutritionalInformationText
import kotlinx.android.synthetic.main.activity_product_details.productOriginText
import kotlinx.android.synthetic.main.activity_product_details.productProducerSaysText
import kotlinx.android.synthetic.main.activity_product_details.productPropertiesLayout
import kotlinx.android.synthetic.main.activity_product_details.productServesText
import kotlinx.android.synthetic.main.activity_product_details.productShelflifeText
import kotlinx.android.synthetic.main.activity_product_details.productStorageInformationText
import kotlinx.android.synthetic.main.activity_product_details.productTitleText
import kotlinx.android.synthetic.main.activity_product_details.quantityRemainingTextView
import kotlinx.android.synthetic.main.activity_product_details.singleVariantTextView
import kotlinx.android.synthetic.main.activity_product_details.toolBarTitle
import kotlinx.android.synthetic.main.activity_product_details.toolbar
import kotlinx.android.synthetic.main.activity_product_details.variantsLayout
import kotlinx.android.synthetic.main.activity_product_details.variantsRadioGroup
import kotlinx.android.synthetic.main.activity_product_details.variantsSpinner
import kotlinx.android.synthetic.main.item_product_property.view.productPropertyImage
import kotlinx.android.synthetic.main.item_product_property.view.productPropertyName
import kotlinx.android.synthetic.main.producer_card_view.producerCardView
import kotlinx.android.synthetic.main.view_add_to_basket_button.addItemToBasketButton
import uk.co.farmdrop.library.di.activity.HasActivitySubcomponentBuilders
import uk.co.farmdrop.library.di.view.HasViewSubcomponentBuilders
import uk.co.farmdrop.library.di.view.ViewComponent
import uk.co.farmdrop.library.di.view.ViewComponentBuilder
import uk.co.farmdrop.library.di.view.ViewModule
import uk.co.farmdrop.library.extensions.getColour
import uk.co.farmdrop.library.ui.ColorConstants
import uk.co.farmdrop.library.utils.Constants.INVALID_ID
import uk.co.farmdrop.library.utils.Constants.ZERO
import uk.co.farmdrop.library.utils.image.ImageManager
import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

class ProductDetailsActivity : BaseActivity(), ProductDetailsActivityContract.View, AppBarLayout.OnOffsetChangedListener,
    HasViewSubcomponentBuilders, ProductsView.ProductsViewListener, ProductDetailsAddToBasketView.Listener {

    companion object {
        private const val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.75f
        private const val PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f
        private const val ALPHA_ANIMATIONS_DURATION = 200L
        private const val BUTTON_ALPHA_ANIMATIONS_DURATION = 400L

        fun getStartingIntent(context: Context, productId: Int = INVALID_ID, graphQLProductId: String? = null) =
            Intent(context, ProductDetailsActivity::class.java).apply {
                if (productId != INVALID_ID) {
                    putExtra(Constants.PRODUCT_ID, productId)
                }
                if (graphQLProductId != null) {
                    putExtra(Constants.GRAPHQL_PRODUCT_ID, graphQLProductId)
                }
            }
    }

    @Inject
    lateinit var viewComponentBuilders: Map<Class<out View>, @JvmSuppressWildcards Provider<ViewComponentBuilder<*, *>>>

    @Inject
    lateinit var presenter: ProductDetailsActivityPresenter

    @field:Named(AppModule.NAME_UNIT_DECIMAL_FORMAT)
    @Inject
    lateinit var unitDecimalFormat: DecimalFormat

    private var productId: Int = INVALID_ID

    private var graphQLProductId: String? = null

    private val rect = Rect()

    private var isTheTitleVisible = false
    private var isTheTitleContainerVisible = true
    private var isBottomAddToBasketButtonVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        presenter.attachView(this)
        addToBasketTopView.attachView()
        addToBasketBottomView.attachView()
        initToolbar()
        variantsRadioGroup.setOnCheckedChangeListener { radioGroup, id ->
            run {
                val variantIndex = radioGroup.indexOfChild(radioGroup.findViewById(id))
                presenter.onVariantSelected(variantIndex)
            }
        }

        variantsSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.onVariantSelected(position)
            }
        }
        producerCardView.setBackgroundResource(R.drawable.rounded_background_grey5_border)
        nestedScrollView.setOnScrollChangeListener { v: NestedScrollView?, _: Int, _: Int, _: Int, _: Int ->
            v?.let {
                it.getDrawingRect(rect)
                if (addToBasketTopView.getLocalVisibleRect(rect)) {
                    if (isBottomAddToBasketButtonVisible) {
                        startAlphaAnimation(addToBasketBottomView, BUTTON_ALPHA_ANIMATIONS_DURATION, View.INVISIBLE)
                        isBottomAddToBasketButtonVisible = false
                    }
                } else {
                    if (!isBottomAddToBasketButtonVisible) {
                        startAlphaAnimation(addToBasketBottomView, BUTTON_ALPHA_ANIMATIONS_DURATION, View.VISIBLE)
                        isBottomAddToBasketButtonVisible = true
                    }
                }
            }
        }
        presenter.listenForBasketChangeEvents()
        presenter.loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        presenter.destroyAllDisposables()
        addToBasketTopView.detachView()
        addToBasketBottomView.detachView()
        appBarLayout.removeOnOffsetChangedListener(this)
    }

    override fun injectMembers(hasActivitySubComponentBuilders: HasActivitySubcomponentBuilders) {
        (hasActivitySubComponentBuilders
            .getActivityComponentBuilder(ProductDetailsActivity::class.java) as ProductDetailsActivityComponent.Builder)
            .activityModule(ProductDetailsActivityComponent.ProductDetailsActivityModule(this, productId, graphQLProductId))
            .build()
            .injectMembers(this)
    }

    override fun getViewComponentBuilder(viewClass: Class<out View>?): ViewComponentBuilder<out ViewModule<*>, out ViewComponent<*>> =
        viewComponentBuilders[viewClass]!!.get()

    private fun initToolbar() {
        setToolbar(toolbar)
        setHomeAsUpEnabled(R.drawable.ic_arrow_back_white)
        startAlphaAnimation(toolBarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        collapsingToolbar.title = String.EMPTY
        supportActionBar?.title = String.EMPTY
        appBarLayout.addOnOffsetChangedListener(this)
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTextAppearance)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun createFragment() {
    }

    override fun retrieveIntentBundle(bundle: Bundle) {
        if (bundle.containsKey(Constants.PRODUCT_ID)) {
            productId = bundle.getInt(Constants.PRODUCT_ID)
        }
        if (bundle.containsKey(Constants.GRAPHQL_PRODUCT_ID)) {
            graphQLProductId = bundle.getString(Constants.GRAPHQL_PRODUCT_ID)
        }
    }

    override fun showEmptyDetailsText() {
        emptyDetailsText.visibility = View.VISIBLE
    }

    override fun hideDetailsText() {
        emptyDetailsText.visibility = View.GONE
    }

    override fun displayProductName(name: String) {
        productTitleText.text = name
        toolBarTitle.text = name
    }

    override fun displayProductIntroParagraph(introParagraph: String) {
        productIntroParagraphMarkdown.loadMarkdown(introParagraph)
    }

    override fun displayProductDescription(description: String) {
        productDescriptionMarkdown.loadMarkdown(description)
    }

    override fun displayProducerName(name: String) {
        producerTitleText.text = name
        productProducerSaysText.text = getString(R.string.producer_says, name)
        meetProducerTextView.text = getString(R.string.meet_producer_name, name)
        moreFromProducerTextView.text = getString(R.string.more_from_producer_name, name)
    }

    override fun displayProductPicture(pictureUrl: String) {
        val screenDimensions = getScreenDimensionsInPixels()
        val imageHeight = resources.getDimensionPixelSize(R.dimen.producer_details_image_height)
        ImageManager.loadImageFromPixelSize(this, pictureUrl, productImage, R.drawable.ic_placeholder_image_selfie_carrot, screenDimensions.x, imageHeight, null, ImageManager.CROP_MODE_FACE)
        productImage.setOnClickListener {
            startActivity(FullScreenPhotoActivity.getStartingIntent(this, pictureUrl))
        }
    }

    override fun displayDefaultProductPicture(placeHolder: Int) {
        productImage.setImageResource(R.drawable.ic_placeholder_image_selfie_carrot)
    }

    override fun displayVariantSingleText(variant: String) {
        singleVariantTextView.visibility = View.VISIBLE
        singleVariantTextView.text = variant
    }

    override fun hideVariantSingleText() {
        singleVariantTextView.visibility = View.GONE
    }

    override fun initVariantsRadioGroup() {
        variantsRadioGroup.removeAllViews()
        variantsLayout.visibility = View.VISIBLE
    }

    override fun addVariantRadioButton(variant: String) {
        val radioButton = LayoutInflater.from(this).inflate(R.layout.radio_button_variant, variantsLayout, false) as RadioButton
        radioButton.text = variant
        variantsRadioGroup.addView(radioButton)
    }

    override fun hideVariantsRadioGroup() {
        variantsRadioGroup.visibility = View.GONE
    }

    override fun selectVariantRadioButton(index: Int) {
        if (index >= ZERO && index < variantsRadioGroup.childCount) {
            (variantsRadioGroup.getChildAt(index) as RadioButton).isChecked = true
        }
    }

    override fun displayVariantsDropDown(variants: List<String?>) {
        variantsLayout.visibility = View.VISIBLE
        variantsSpinner.visibility = View.VISIBLE

        val adapter = ArrayAdapter(this, R.layout.spinner_item_variant, variants)
        variantsSpinner.adapter = adapter
    }

    override fun hideVariantsDropDown() {
        variantsSpinner.visibility = View.GONE
    }

    override fun selectVariantDropDown(index: Int) {
        variantsSpinner.setSelection(index)
    }

    override fun createVariantText(unit: String, value: Double, valueType: String): String =
        getString(R.string.variant_unit_value_type, unit, unitDecimalFormat.format(value), valueType)

    override fun createVariantText(value: Double, valueType: String): String =
        getString(R.string.variant_value_type, unitDecimalFormat.format(value), valueType)

    override fun showMarketingTag(name: String) {
        marketingTagTextView.text = name
        marketingTagTextView.visibility = View.VISIBLE
    }

    override fun hideMarketingTag() {
        marketingTagTextView.visibility = View.INVISIBLE
    }

    override fun displayOrigin(origin: String) {
        val text = String.format(getString(R.string.produced_in, origin))
        productOriginText.text = text.toSpanned()
        productOriginText.visibility = View.VISIBLE
    }

    override fun displayServes(serves: String) {
        val text = String.format(getString(R.string.serves, serves))
        productServesText.text = text.toSpanned()
        productServesText.visibility = View.VISIBLE
    }

    override fun displayShelfLife(shelfLifeDays: String) {
        val text = String.format(getString(R.string.shelf_life, shelfLifeDays))
        productShelflifeText.text = text.toSpanned()
        productShelflifeText.visibility = View.VISIBLE
    }

    override fun showPropertiesLayout() {
        productPropertiesLayout.visibility = View.VISIBLE
    }

    override fun hidePropertiesLayout() {
        productPropertiesLayout.visibility = View.GONE
    }

    override fun addProperty(propertyData: TagData) {
        val productProperty = layoutInflater.inflate(R.layout.item_product_property, productPropertiesLayout, false)
        productProperty.productPropertyName.text = propertyData.name
        propertyData.icon?.url?.let { iconUrl ->
            ImageManager.loadVectorFromUrl(this, iconUrl, productProperty.productPropertyImage,
                ImageManager.NO_PLACEHOLDER, SvgSoftwareLayerSetterColorFilter(this, R.color.farmdropGreen))
        }
        productPropertiesLayout.addView(productProperty)
    }

    override fun displayAwards(awards: List<MediaData>) {
        val size = resources.getDimensionPixelSize(R.dimen.product_award_icon_size)
        awards.forEach { mediaData ->
            mediaData.url?.let { url ->
                val productAwardImage = ImageView(this)
                ImageManager.loadImage(this, url, productAwardImage, INVALID_ID, null)
                val layoutParams = LinearLayout.LayoutParams(size, size)
                productAwardImage.layoutParams = layoutParams
                productAwardsLayout.addView(productAwardImage)
            }
        }
        productAwardsText.visibility = View.VISIBLE
        productAwardsLayout.visibility = View.VISIBLE
    }

    override fun displayIngredients() {
        productIngredientsText.visibility = View.VISIBLE
        productIngredientsText.setOnClickListener {
            launchMoreInfoActivity(INGREDIENTS, getString(R.string.ingredients))
        }
    }

    override fun displayNutritionalInformation() {
        productNutritionalInformationText.visibility = View.VISIBLE
        productNutritionalInformationText.setOnClickListener {
            launchMoreInfoActivity(NUTRITIONAL_INFO, getString(R.string.nutritional_information))
        }
    }

    override fun displayCookingInstructions() {
        productCookingInstructionsText.visibility = View.VISIBLE
        productCookingInstructionsText.setOnClickListener {
            launchMoreInfoActivity(COOKING_INSTRUCTIONS, getString(R.string.cooking_instructions))
        }
    }

    override fun displayStorageInformation() {
        productStorageInformationText.visibility = View.VISIBLE
        productStorageInformationText.setOnClickListener {
            launchMoreInfoActivity(STORAGE_INFO, getString(R.string.storage_information))
        }
    }

    private fun launchMoreInfoActivity(@ProductMoreInfoTypes type: Int, title: String) {
        presenter.productData?.graphQLId?.let {
            val intent = ProductMoreInfoActivity.getStartingIntent(this, it, type, title)
            startActivity(intent)
        }
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        val maxScroll = appBarLayout.totalScrollRange
        val percentage = Math.abs(verticalOffset).toFloat() / maxScroll.toFloat()

        handleAlphaOnTitle(percentage)
        handleToolbarVisibility(percentage)
    }

    private fun handleToolbarVisibility(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {

            if (!isTheTitleVisible) {
                startAlphaAnimation(toolBarTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE)
                toolbar.setBackgroundColor(getColour(R.color.farmdropGreen))
                isTheTitleVisible = true
            }
        } else {

            if (isTheTitleVisible) {
                startAlphaAnimation(toolBarTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE)
                toolbar.setBackgroundColor(getColour(android.R.color.transparent))
                isTheTitleVisible = false
            }
        }
    }

    private fun handleAlphaOnTitle(percentage: Float) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if (isTheTitleContainerVisible) {
                startAlphaAnimation(linearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE)
                isTheTitleContainerVisible = false
            }
        } else {

            if (!isTheTitleContainerVisible) {
                startAlphaAnimation(linearlayoutTitle, ALPHA_ANIMATIONS_DURATION, View.VISIBLE)
                isTheTitleContainerVisible = true
            }
        }
    }

    private fun startAlphaAnimation(v: View, duration: Long, visibility: Int) {
        val alphaAnimation = if (visibility == View.VISIBLE) {
            AlphaAnimation(ColorConstants.TRANSPARENT_ALPHA, ColorConstants.OPAQUE_ALPHA)
        } else {
            AlphaAnimation(ColorConstants.OPAQUE_ALPHA, ColorConstants.TRANSPARENT_ALPHA)
        }

        alphaAnimation.duration = duration
        alphaAnimation.fillAfter = true
        v.startAnimation(alphaAnimation)
    }

    override fun displayProducerDetailsView(producerData: ProducerData) {
        producerCardView.bind(producerData)
        producerCardView.setOnClickListener {
            startActivity(ProducerDetailsActivity.getStartingIntent(this, producerData.id))
        }
    }

    override fun hideMeetProducerTopLine() {
        meetProducerTopLineView.visibility = View.GONE
    }

    override fun showMeetProducerTopLine() {
        meetProducerTopLineView.visibility = View.VISIBLE
    }

    override fun displayProducerProductsCarousel(producerId: Int) {
        val productsView = ProductsView(this, producerId = producerId, productViewLocation = ProductViewLocationFactory.getProductViewLocation(ProductViewLocation.PRODUCT_TILE_PRODUCT_PAGE))
        productsView.productsViewListener = this
        productsView.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        moreProductsCarousel.addView(productsView)
    }

    override fun onProductsViewEmptyResult() {
        moreProductsCarousel.visibility = View.GONE
        moreFromProducerTextView.visibility = View.GONE
    }

    override fun onProductSelected(selectedProduct: ProductData) {
        startActivity(ProductDetailsActivity.getStartingIntent(this, graphQLProductId = selectedProduct.graphQLId))
    }

    override fun onAddItemPressed() {
        val productViewLocation = ProductViewLocationFactory.getProductViewLocation(ProductViewLocation.PRODUCT_DETAIL)
        presenter.onAddItemToBasketClick(productViewLocation)
    }

    override fun onRemoveItemPressed() {
        val productViewLocation = ProductViewLocationFactory.getProductViewLocation(ProductViewLocation.PRODUCT_DETAIL)
        presenter.onRemoveItemFromBasketClick(productViewLocation)
    }

    override fun displayPrice(price: Double) {
        addToBasketTopView.displayPrice(price)
        addToBasketBottomView.displayPrice(price)
    }

    override fun displayDiscountedPrice(price: Double, oldPrice: Double) {
        addToBasketTopView.displayDiscountedPrice(price, oldPrice)
        addToBasketBottomView.displayDiscountedPrice(price, oldPrice)
    }

    override fun hideAddToBasketButton() {
        addToBasketTopView.displayAsItemsInBasket()
        addToBasketBottomView.displayAsItemsInBasket()
    }

    override fun showAddToBasketButton() {
        addToBasketTopView.displayAsZeroInBasket()
        addToBasketBottomView.displayAsZeroInBasket()
    }

    override fun updateBasketItemsNumber(number: Int) {
        addToBasketTopView.setTotalInBasket(number)
        addToBasketBottomView.setTotalInBasket(number)
    }

    override fun trackAddedProduct(properties: Properties) {
        AnalyticsHelper.trackEventWithDataSource(this, AnalyticsEvents.ADDED_PRODUCT_EVENT, properties)
    }

    override fun trackRemovedProduct(properties: Properties) {
        AnalyticsHelper.trackEventWithDataSource(this, AnalyticsEvents.REMOVED_PRODUCT_EVENT, properties)
    }

    override fun showQuantityRemaining(quantity: Int) {
        quantityRemainingTextView.visibility = View.VISIBLE
        quantityRemainingTextView.text = getString(R.string.quantity_remaining, quantity)
    }

    override fun hideQuantityRemaining() {
        quantityRemainingTextView.visibility = View.GONE
    }

    override fun showItemsSoldOut() {
    }

    override fun hideItemsSoldOut() {
    }

    override fun enableAddToBasketMultipleItemButton() {
        addItemToBasketButton.isEnabled = true
    }

    override fun disableAddToBasketMultipleItemButton() {
        addItemToBasketButton.isEnabled = false
    }

    override fun formatVariantDisplayNameWithUnitWithPricePerUnit(displayNameWithUnit: String, pricePerUnit: String): String =
        getString(R.string.variant_display_name_price_per_unit, displayNameWithUnit, pricePerUnit)
}
