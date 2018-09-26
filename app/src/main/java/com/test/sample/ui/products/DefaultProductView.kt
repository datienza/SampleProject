package com.farmdrop.customer.ui.products

import android.content.Context
import android.util.AttributeSet
import com.farmdrop.customer.contracts.products.ProductViewContract
import com.farmdrop.customer.presenters.products.ProductViewPresenter
import com.farmdrop.customer.utils.analytics.ProductViewLocation
import uk.co.farmdrop.library.di.view.HasViewSubcomponentBuilders

class DefaultProductView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : BaseProductView<ProductViewContract.View, ProductViewPresenter>(context, attrs, defStyleAttr) {

    override fun initPresenter() {
        presenter.attachView(this)
    }

    override fun setupViewComponent(viewSubcomponentBuilders: HasViewSubcomponentBuilders) {
        (viewSubcomponentBuilders.getViewComponentBuilder(DefaultProductView::class.java) as DefaultProductViewComponent.Builder)
                .viewModule(DefaultProductViewComponent.ProductViewModule(this))
                .build()
                .injectMembers(this)
    }

    fun bind(product: ProductData, productViewLocationData: ProductViewLocation?, position: Int) {
        productViewLocation = productViewLocationData
        presenter.bind(product, productViewLocation, position)
    }
}
