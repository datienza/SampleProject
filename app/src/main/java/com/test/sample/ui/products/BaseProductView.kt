package com.farmdrop.customer.ui.products

import android.content.Context
import android.graphics.Paint
import android.support.annotation.DimenRes
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import com.farmdrop.customer.contracts.products.BaseProductContract
import com.farmdrop.customer.contracts.products.ProductViewContract
import com.farmdrop.customer.di.app.AppModule
import com.farmdrop.customer.presenters.products.BaseProductPresenter
import com.farmdrop.customer.utils.analytics.AnalyticsEvents
import com.farmdrop.customer.utils.analytics.AnalyticsHelper
import com.farmdrop.customer.utils.analytics.ProductViewLocation
import com.segment.analytics.Properties
import com.test.sample.R
import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Named

abstract class BaseProductView<V : BaseProductContract.View, P : BaseProductPresenter<V>> @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : CardView(context, attrs, defStyleAttr), ProductViewContract.View {

    @Inject
    lateinit var presenter: P

    @field:Named(AppModule.NAME_PRICE_DECIMAL_FORMAT)
    @Inject
    lateinit var priceDecimalFormat: DecimalFormat

    @field:Named(AppModule.NAME_UNIT_DECIMAL_FORMAT)
    @Inject
    lateinit var unitDecimalFormat: DecimalFormat

    protected val addToBasketButton: Button by lazy(LazyThreadSafetyMode.NONE) { findViewById<Button>(R.id.addToBasketButton) }
    protected val addToBasketMultipleItemButton: Button by lazy(LazyThreadSafetyMode.NONE) { findViewById<Button>(R.id.addToBasketMultipleItemButton) }
    private val removeFromBasketMultipleItemButton by lazy(LazyThreadSafetyMode.NONE) { findViewById<Button>(R.id.removeFromBasketMultipleItemButton) }
    private val variantsRadioGroup by lazy(LazyThreadSafetyMode.NONE) { findViewById<RadioGroup>(R.id.variantsRadioGroup) }
    private val variantsSpinner by lazy(LazyThreadSafetyMode.NONE) { findViewById<Spinner>(R.id.variantsSpinner) }
    private val producerName by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.producerName) }
    private val productName by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.productName) }
    private val productImage by lazy(LazyThreadSafetyMode.NONE) { findViewById<ImageView>(R.id.productImage) }
    private val priceTextView by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.priceTextView) }
    private val secondaryPriceTextView by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.secondaryPriceTextView) }
    private val singleVariantTextView by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.singleVariantTextView) }
    private val variantsLayout by lazy(LazyThreadSafetyMode.NONE) { findViewById<LinearLayout>(R.id.variantsLayout) }
    private val addRemoveBasketMultipleItemsLayout by lazy(LazyThreadSafetyMode.NONE) { findViewById<RelativeLayout>(R.id.addRemoveBasketMultipleItemsLayout) }
    private val basketItemsNumberTextView by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.basketItemsNumberTextView) }
    private val marketingTagTextView by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.marketingTagTextView) }
    private val productImageOverlayLayout by lazy(LazyThreadSafetyMode.NONE) { findViewById<LinearLayout>(R.id.productImageOverlayLayout) }
    protected val productImageOverlayItemsNumberTextView: TextView by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.productImageOverlayItemsNumberTextView) }
    private val quantityRemainingTextView by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.quantityRemainingTextView) }
    private val productImageOverlayItemsSoldOutTextView by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.productImageOverlayItemsSoldOutTextView) }

    @DimenRes
    protected var baseProductImageWidthRes: Int = R.dimen.product_image_width

    @DimenRes
    protected var baseProductImageHeightRes: Int = R.dimen.product_image_height

    protected var productViewLocation: ProductViewLocation? = null

    init {
        setupViewComponent(context as HasViewSubcomponentBuilders)
        initPresenter()
    }

    abstract fun initPresenter()

    abstract fun setupViewComponent(viewSubcomponentBuilders: HasViewSubcomponentBuilders)

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        addToBasketButton.setOnClickListener {
            presenter.onAddItemToBasketClick(productViewLocation)
        }
        addToBasketMultipleItemButton.setOnClickListener {
            presenter.onAddItemToBasketClick(productViewLocation)
        }
        removeFromBasketMultipleItemButton.setOnClickListener {
            presenter.onRemoveItemFromBasketClick(productViewLocation)
        }
        variantsRadioGroup?.setOnCheckedChangeListener { radioGroup, id ->
            run {
                val variantIndex = radioGroup.indexOfChild(radioGroup.findViewById(id))
                presenter.onVariantSelected(variantIndex)
            }
        }
        variantsSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                presenter.onVariantSelected(position)
            }
        }
    }

    override fun displayProductName(name: String) {
        productName.text = name
    }

    override fun displayProducerName(name: String) {
        producerName.text = name
    }

    override fun displayProductPicture(pictureUrl: String) {
        ImageManager.loadImageFromDimenResSize(context, pictureUrl, productImage, R.drawable.ic_placeholder_image_selfie_carrot,
                baseProductImageWidthRes, baseProductImageHeightRes, null)
    }

    override fun displayDefaultProductPicture(placeHolder: Int) {
        productImage.setImageResource(R.drawable.ic_placeholder_image_selfie_carrot)
    }

    override fun displayPrice(price: Double) {
        priceTextView.setTextColor(context.getColour(R.color.farmdropBlack))
        priceTextView.text = context.getString(R.string.price, priceDecimalFormat.format(price))
    }

    override fun displaySalePrice(price: Double) {
        priceTextView.setTextColor(context.getColour(R.color.warningTomato))
        priceTextView.text = context.getString(R.string.price, priceDecimalFormat.format(price))
    }

    override fun displayOldPrice(oldPrice: Double) {
        secondaryPriceTextView.paintFlags = secondaryPriceTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        secondaryPriceTextView.text = context.getString(R.string.price, priceDecimalFormat.format(oldPrice))
        secondaryPriceTextView.visibility = View.VISIBLE
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
        variantsRadioGroup.visibility = View.VISIBLE
    }

    override fun addVariantRadioButton(variant: String) {
        val radioButton = LayoutInflater.from(context).inflate(R.layout.radio_button_variant, variantsLayout, false) as RadioButton
        radioButton.text = variant
        variantsRadioGroup.addView(radioButton)
    }

    override fun hideVariantsRadioGroup() {
        variantsRadioGroup.visibility = View.GONE
    }

    override fun selectVariantRadioButton(index: Int) {
        if (index >= 0 && index < variantsRadioGroup.childCount) {
            (variantsRadioGroup.getChildAt(index) as RadioButton).isChecked = true
        }
    }

    override fun displayVariantsDropDown(variants: List<String?>) {
        variantsSpinner.visibility = View.VISIBLE

        val adapter = ArrayAdapter(context, R.layout.spinner_item_variant, variants)
        variantsSpinner.adapter = adapter
    }

    override fun hideVariantsDropDown() {
        variantsSpinner.visibility = View.GONE
    }

    override fun selectVariantDropDown(index: Int) {
        variantsSpinner.setSelection(index)
    }

    override fun createVariantText(unit: String, value: Double, valueType: String): String =
            context.getString(R.string.variant_unit_value_type, unit, unitDecimalFormat.format(value), valueType)

    override fun createVariantText(value: Double, valueType: String): String =
            context.getString(R.string.variant_value_type, unitDecimalFormat.format(value), valueType)

    override fun hideAddToBasketButton() {
        addToBasketButton.visibility = View.GONE
    }

    override fun showAddToBasketButton() {
        addToBasketButton.visibility = View.VISIBLE
    }

    override fun hideBasketMultipleItemsLayout() {
        addRemoveBasketMultipleItemsLayout.visibility = View.GONE
    }

    override fun showBasketMultipleItemsLayout() {
        addRemoveBasketMultipleItemsLayout.visibility = View.VISIBLE
    }

    override fun updateBasketItemsNumber(number: Int) {
        basketItemsNumberTextView.text = number.toString()
    }

    override fun showMarketingTag(name: String) {
        marketingTagTextView?.text = name
        marketingTagTextView?.visibility = View.VISIBLE
    }

    override fun hideMarketingTag() {
        marketingTagTextView?.visibility = View.GONE
    }

    override fun hideProductImageOverlay() {
        productImageOverlayLayout.visibility = View.GONE
    }

    override fun showProductImageOverlay() {
        productImageOverlayLayout.visibility = View.VISIBLE
    }

    override fun updateProductImageOverlayItemsNumber(number: Int) {
        productImageOverlayItemsNumberTextView.text = context.getString(R.string.quantity_in_basket, number)
    }

    override fun showQuantityRemaining(quantity: Int) {
        quantityRemainingTextView.visibility = View.VISIBLE
        quantityRemainingTextView.text = context.getString(R.string.quantity_remaining, quantity)
    }

    override fun hideQuantityRemaining() {
        quantityRemainingTextView.visibility = View.GONE
    }

    override fun trackAddedProduct(properties: Properties) {
        context?.let {
            AnalyticsHelper.trackEventWithDataSource(it, AnalyticsEvents.ADDED_PRODUCT_EVENT, properties)
        }
    }

    override fun trackRemovedProduct(properties: Properties) {
        context?.let {
            AnalyticsHelper.trackEventWithDataSource(it, AnalyticsEvents.REMOVED_PRODUCT_EVENT, properties)
        }
    }

    override fun showItemsSoldOut() {
        productImageOverlayItemsSoldOutTextView.visibility = View.VISIBLE
    }

    override fun hideItemsSoldOut() {
        productImageOverlayItemsSoldOutTextView.visibility = View.GONE
    }

    override fun enableAddToBasketMultipleItemButton() {
        addToBasketMultipleItemButton.isEnabled = true
    }

    override fun disableAddToBasketMultipleItemButton() {
        addToBasketMultipleItemButton.isEnabled = false
    }

    override fun displayPricePerUnit(pricePerUnit: String) {
        secondaryPriceTextView.paintFlags = secondaryPriceTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()

        secondaryPriceTextView.visibility = View.VISIBLE
        secondaryPriceTextView.text = pricePerUnit
    }

    override fun hideSecondaryPrice() {
        secondaryPriceTextView.visibility = View.GONE
    }
}
