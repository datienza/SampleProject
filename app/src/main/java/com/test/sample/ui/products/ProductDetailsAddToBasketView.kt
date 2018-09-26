package com.farmdrop.customer.ui.products

import android.content.Context
import android.graphics.Paint
import android.support.annotation.ColorRes
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.products.ProductDetailsAddToBasketViewContract
import com.farmdrop.customer.di.app.AppModule
import uk.co.farmdrop.library.extensions.getColour
import com.farmdrop.customer.presenters.products.ProductDetailsAddToBasketViewPresenter
import uk.co.farmdrop.library.utils.Constants.ZERO
import kotlinx.android.synthetic.main.view_add_to_basket_button.view.addItemToBasketButton
import kotlinx.android.synthetic.main.view_add_to_basket_button.view.addToBasketText
import kotlinx.android.synthetic.main.view_add_to_basket_button.view.basketItemsNumberTextView
import kotlinx.android.synthetic.main.view_add_to_basket_button.view.basketQuantityLayout
import kotlinx.android.synthetic.main.view_add_to_basket_button.view.productOldPriceText
import kotlinx.android.synthetic.main.view_add_to_basket_button.view.productPriceText
import kotlinx.android.synthetic.main.view_add_to_basket_button.view.removeItemFromBasketButton
import uk.co.farmdrop.library.di.view.HasViewSubcomponentBuilders
import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Named

class ProductDetailsAddToBasketView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr), ProductDetailsAddToBasketViewContract.View {

    companion object {
        internal const val EMPTY_BASKET_BACKGROUND_LAYER = 0
        internal const val POPULATED_BASKET_BACKGROUND_LAYER = 1
    }

    @field:Named(AppModule.NAME_PRICE_DECIMAL_FORMAT)
    @Inject
    lateinit var mPriceDecimalFormat: DecimalFormat

    @Inject
    lateinit var mPresenter: ProductDetailsAddToBasketViewPresenter

    private lateinit var mListener: Listener

    init {
        setupViewComponent()
        setBackgroundResource(R.drawable.button_add_to_basket_background)
        gravity = Gravity.CENTER_VERTICAL
        orientation = HORIZONTAL
        val padding = resources.getDimensionPixelSize(R.dimen.grid_spacing_double)
        setPadding(padding, ZERO, padding, ZERO)
        View.inflate(context, R.layout.view_add_to_basket_button, this)
    }

    private fun setupViewComponent() {
        ((context as HasViewSubcomponentBuilders).getViewComponentBuilder(ProductDetailsAddToBasketView::class.java) as ProductDetailsAddToBasketViewComponent.Builder)
                .viewModule(ProductDetailsAddToBasketViewComponent.ProductDetailsAddToBasketViewModule(this))
                .build()
                .injectMembers(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (context is ProductDetailsAddToBasketView.Listener) {
            mListener = context as ProductDetailsAddToBasketView.Listener
        } else {
            throw IllegalStateException(context::class.simpleName + " must implement ProductDetailsAddToBasketView.Listener")
        }

        addItemToBasketButton.setOnClickListener {
            mListener.onAddItemPressed()
        }
        removeItemFromBasketButton.setOnClickListener {
            mListener.onRemoveItemPressed()
        }
    }

    fun attachView() = mPresenter.attachView(this)

    fun detachView() = mPresenter.detachView()

    fun displayPrice(price: Double) = mPresenter.displayPrice(price)

    fun displayDiscountedPrice(price: Double, oldPrice: Double) = mPresenter.displayDiscountedPrice(price, oldPrice)

    fun setTotalInBasket(totalToDisplay: Int) = mPresenter.setTotalInBasket(totalToDisplay)

    fun displayAsZeroInBasket() = mPresenter.displayAsZeroInBasket()

    fun displayAsItemsInBasket() = mPresenter.displayAsItemsInBasket()

    override fun setTotalInBasketText(totalInBasket: String) {
        basketItemsNumberTextView.text = totalInBasket
    }

    override fun getBackgroundLevel() = background.level

    override fun setToBasketEmptyBackground() {
        background.level = EMPTY_BASKET_BACKGROUND_LAYER
    }

    override fun setToItemsInBasketBackground() {
        background.level = POPULATED_BASKET_BACKGROUND_LAYER
    }

    override fun showPlusMinusBasketButtons() {
        basketQuantityLayout.visibility = View.VISIBLE
    }

    override fun hidePlusMinusBasketButtons() {
        basketQuantityLayout.visibility = View.GONE
    }

    override fun showAddToBasketText() {
        addToBasketText.visibility = View.VISIBLE
    }

    override fun hideAddToBasketText() {
        addToBasketText.visibility = View.GONE
    }

    override fun setProductPriceTextColor(@ColorRes colorRes: Int) {
        val colour = context.getColour(colorRes)
        productPriceText.setTextColor(colour)
    }

    override fun setProductPriceText(price: Double) {
        productPriceText.text = context.getString(R.string.price, mPriceDecimalFormat.format(price))
    }

    override fun setProductOldPriceTextColor(@ColorRes colorRes: Int) {
        val colour = context.getColour(colorRes)
        productOldPriceText.setTextColor(colour)
    }

    override fun setProductOldPriceText(price: Double) {
        productOldPriceText.text = context.getString(R.string.price, mPriceDecimalFormat.format(price))
    }

    override fun setProductOldPriceStrikeThrough() {
        productOldPriceText.paintFlags = productOldPriceText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    }

    override fun showProductOldPriceText() {
        productOldPriceText.visibility = View.VISIBLE
    }

    override fun hideProductOldPriceText() {
        productOldPriceText.visibility = View.GONE
    }

    override fun clearWholeViewClickListener() = setOnClickListener(null)

    override fun setWholeViewClickListener() = setOnClickListener { mListener.onAddItemPressed() }

    interface Listener {
        fun onAddItemPressed()
        fun onRemoveItemPressed()
    }
}
