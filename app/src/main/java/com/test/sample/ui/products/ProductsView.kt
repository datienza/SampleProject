package com.farmdrop.customer.ui.products

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.products.ProductsViewContract
import com.farmdrop.customer.presenters.products.ProductsViewPresenter
import com.farmdrop.customer.ui.adapter.products.ProductsAdapter
import com.farmdrop.customer.ui.common.BaseListView
import com.farmdrop.customer.utils.analytics.ProductViewLocation
import kotlinx.android.synthetic.main.view_base_list.view.baseListRecyclerView
import uk.co.farmdrop.library.di.view.HasViewSubcomponentBuilders
import uk.co.farmdrop.library.ui.SpacingItemDecoration

class ProductsView(
    context: Context,
    private val name: String? = null,
    private val producerId: Int = 0,
    private val productViewLocation: ProductViewLocation? = null
) : BaseListView<ProductsViewPresenter, ProductsAdapter, ProductData, ProductsAdapter.ProductViewHolder, ProductsViewContract.View>(context), ProductsViewContract.View {

    var productsViewListener: ProductsViewListener? = null

    init {
        setupViewComponent()
        inflate(context, getLayoutRes(), this)
        mPresenter.attachView(this)
    }

    private fun setupViewComponent() {
        ((context as HasViewSubcomponentBuilders).getViewComponentBuilder(ProductsView::class.java) as ProductsViewComponent.Builder)
                .viewModule(ProductsViewComponent.ProductsViewModule(this, name, producerId, productViewLocation))
                .build()
                .injectMembers(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mPresenter.listenForBasketChangeEvents()
        loadDataForList(false)
    }

    override fun getLayoutManager(): RecyclerView.LayoutManager {
        val orientation = if (producerId != 0) LinearLayoutManager.HORIZONTAL else LinearLayoutManager.VERTICAL
        return LinearLayoutManager(context, orientation, false)
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val spacingItemDecoration = SpacingItemDecoration(context)
        if (producerId != 0) {
            spacingItemDecoration.setHorizontalSpacing(R.dimen.producer_details_products_items_spacing)
        } else {
            spacingItemDecoration.setVerticalSpacing(R.dimen.grid_spacing_half)
        }
        return spacingItemDecoration
    }

    override fun setUpList() {
        super.setUpList()
        if (producerId != 0) {
            val padding = resources.getDimensionPixelSize(R.dimen.producer_details_products_list_padding_horizontal)
            baseListRecyclerView.setPadding(padding, 0, padding, 0)
            baseListRecyclerView.clipToPadding = false
        } else {
            val padding = resources.getDimensionPixelSize(R.dimen.grid_spacing_quarter)
            baseListRecyclerView.setPadding(0, padding, 0, padding)
        }
        baseListRecyclerView.itemAnimator = null
        mAdapter.onProductSelectedListener = productsViewListener
    }

    override fun notifyItemChanged(itemPosition: Int) {
        mAdapter.notifyItemChanged(itemPosition)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mPresenter.detachView()
        mPresenter.destroyAllDisposables()
    }

    override fun hideViewOnEmptyResult() {
        productsViewListener?.onProductsViewEmptyResult()
    }

    interface ProductsViewListener : OnProductSelectedListener {
        fun onProductsViewEmptyResult()
    }
}
