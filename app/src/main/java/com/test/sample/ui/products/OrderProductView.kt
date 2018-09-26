package com.farmdrop.customer.ui.products

import android.content.Context
import android.util.AttributeSet
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.products.OrderProductViewContract
import com.farmdrop.customer.data.model.ProductData
import com.farmdrop.customer.presenters.products.OrderProductViewPresenter
import com.farmdrop.customer.utils.analytics.ProductViewLocation
import uk.co.farmdrop.library.di.view.HasViewSubcomponentBuilders

class OrderProductView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseProductView<OrderProductViewContract.View, OrderProductViewPresenter>(context, attrs, defStyleAttr), OrderProductViewContract.View {

    init {
        baseProductImageWidthRes = R.dimen.item_order_product_image_width
        baseProductImageHeightRes = R.dimen.item_order_product_height
    }

    override fun initPresenter() {
        presenter.attachView(this)
    }

    override fun setupViewComponent(viewSubcomponentBuilders: HasViewSubcomponentBuilders) {
        (viewSubcomponentBuilders.getViewComponentBuilder(OrderProductView::class.java) as OrderProductViewComponent.Builder)
                .viewModule(OrderProductViewComponent.OrderProductViewModule(this))
                .build()
                .injectMembers(this)
    }

    fun bind(product: ProductData, quantityInOrder: Int, orderProgress: String?, position: Int, productViewLocationData: ProductViewLocation?) {
        productViewLocation = productViewLocationData
        presenter.bind(product, quantityInOrder, orderProgress, position)
    }

    override fun updateProductImageOverlayItemsNumber(number: Int) {
        productImageOverlayItemsNumberTextView.text = context.getString(R.string.qty_formatted, number)
    }

    override fun showItemAsUnavailable() {
        addToBasketButton.setOnClickListener(null)
        addToBasketButton.isEnabled = false
        addToBasketButton.text = context.getString(R.string.unavailable)
    }

    override fun showItemsSoldOut() {
        addToBasketMultipleItemButton.isEnabled = false
    }

    override fun hideItemsSoldOut() {
        addToBasketMultipleItemButton.isEnabled = true
    }

    override fun showAddRemoveButtons() {
        showAddToBasketButton()
        showBasketMultipleItemsLayout()
    }

    override fun hideAddRemoveButtons() {
        hideAddToBasketButton()
        hideBasketMultipleItemsLayout()
    }
}
