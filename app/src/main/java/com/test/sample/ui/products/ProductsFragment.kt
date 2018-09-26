package com.farmdrop.customer.ui.products

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.products.ProductsFragmentContract
import com.farmdrop.customer.data.model.pagination.CursorData
import com.farmdrop.customer.presenters.products.ProductsFragmentPresenter
import com.farmdrop.customer.ui.adapter.products.ProductsAdapter
import com.farmdrop.customer.ui.common.BasePaginatedListFragment
import com.farmdrop.customer.utils.analytics.ProductViewLocation
import com.farmdrop.customer.utils.analytics.ProductViewLocationFactory
import kotlinx.android.synthetic.main.fragment_base_list.baseListRecyclerView
import uk.co.farmdrop.library.di.fragment.HasFragmentSubcomponentBuilders
import uk.co.farmdrop.library.ui.SpacingItemDecoration

class ProductsFragment : BasePaginatedListFragment<ProductsFragmentPresenter, ProductsAdapter, ProductData, ProductsAdapter.ProductViewHolder, ProductsFragmentContract.View, CursorData<ProductData>>(), ProductsFragmentContract.View {

    private var name: String? = null

    private var producerId: Int = 0

    private var onProductSelectedListener: OnProductSelectedListener? = null

    companion object {
        const val NAME = "name"
        const val PRODUCER_ID = "producer_id"

        fun newInstance(name: String? = null, producerId: Int? = 0): ProductsFragment {
            val fragment = ProductsFragment()
            fragment.arguments = Bundle().apply {
                name?.let {
                    putString(NAME, it)
                }
                producerId?.let {
                    putInt(PRODUCER_ID, it)
                }
            }
            return fragment
        }
    }

    override fun injectMembers(hasFragmentSubcomponentBuilders: HasFragmentSubcomponentBuilders) {
        (hasFragmentSubcomponentBuilders
                .getFragmentComponentBuilder(ProductsFragment::class.java) as ProductsFragmentComponent.Builder)
                .fragmentModule(ProductsFragmentComponent.ProductsFragmentModule(this,
                        name,
                        producerId = producerId,
                        productViewLocation = ProductViewLocationFactory.getProductViewLocation(ProductViewLocation.PRODUCT_SUBTAXON)))
                .build()
                .injectMembers(this)
    }

    override fun unpackArguments(bundle: Bundle) {
        if (bundle.containsKey(NAME)) {
            name = bundle.getString(NAME)
        }
        if (bundle.containsKey(PRODUCER_ID)) {
            producerId = bundle.getInt(PRODUCER_ID)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        super.onViewCreated(view, savedInstanceState)
        mPresenter.listenForBasketChangeEvents()
        loadDataForList(false)
        view.contentDescription = getString(R.string.content_description_fragment_products)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnProductSelectedListener) {
            onProductSelectedListener = context
        } else {
            throw IllegalStateException(activity!!::class
                    .simpleName + " must implement " + OnProductSelectedListener::class.java
                    .simpleName)
        }
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
        mAdapter.onProductSelectedListener = onProductSelectedListener
    }

    override fun shouldIncludeSwipeToRefresh(): Boolean = false

    override fun notifyItemChanged(itemPosition: Int) {
        mAdapter.notifyItemChanged(itemPosition)
    }
}
