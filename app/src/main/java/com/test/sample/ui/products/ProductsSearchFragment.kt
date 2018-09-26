package com.farmdrop.customer.ui.products

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.products.ProductsSearchFragmentContract
import com.farmdrop.customer.presenters.products.ProductsSearchFragmentPresenter
import com.farmdrop.customer.ui.adapter.products.ProductsAdapter
import com.farmdrop.customer.ui.common.BaseListFragment
import com.farmdrop.customer.ui.common.Searchable
import com.farmdrop.customer.utils.analytics.AnalyticsEvents
import com.farmdrop.customer.utils.analytics.AnalyticsHelper
import com.farmdrop.customer.utils.analytics.ProductViewLocation
import com.farmdrop.customer.utils.analytics.ProductViewLocationFactory
import com.farmdrop.customer.utils.constants.UiConstants
import kotlinx.android.synthetic.main.fragment_base_list.baseListEmptySearchLayout
import kotlinx.android.synthetic.main.fragment_base_list.baseListLayout
import kotlinx.android.synthetic.main.fragment_base_list.baseListRecyclerView
import uk.co.farmdrop.library.di.fragment.HasFragmentSubcomponentBuilders
import uk.co.farmdrop.library.ui.SpacingItemDecoration
import uk.co.farmdrop.library.ui.helper.AnimationHelper

class ProductsSearchFragment : BaseListFragment<ProductsSearchFragmentPresenter, ProductsAdapter, ProductData, ProductsAdapter.ProductViewHolder, ProductsSearchFragmentContract.View>(), ProductsSearchFragmentContract.View, Searchable {

    companion object {
        fun newInstance() = ProductsSearchFragment()
    }

    private var listener: Listener? = null

    private var productSelectedListener: OnProductSelectedListener? = null

    override fun injectMembers(hasFragmentSubcomponentBuilders: HasFragmentSubcomponentBuilders) {
        (hasFragmentSubcomponentBuilders
                .getFragmentComponentBuilder(ProductsSearchFragment::class.java) as ProductsSearchFragmentComponent.Builder)
                .fragmentModule(ProductsSearchFragmentComponent.ProductsSearchFragmentModule(this, ProductViewLocationFactory.getProductViewLocation(ProductViewLocation.PRODUCT_SUBTAXON)))
                .build()
                .injectMembers(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Listener && context is OnProductSelectedListener) {
            listener = context
            productSelectedListener = context
        } else {
            throw IllegalStateException(context::class.simpleName + " must implement ProductsSearchFragment.Listener and OnProductSelectedListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPresenter.attachView(this)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.contentDescription = getString(R.string.content_description_fragment_products_search)
        super.onViewCreated(view, savedInstanceState)
        listener?.onFragmentViewCreated()
        mPresenter.listenForBasketChangeEvents()
    }

    override fun onStart() {
        super.onStart()
        hideProgressBar()
    }

    override fun onDestroyView() {
        listener?.onFragmentViewDestroyed()
        super.onDestroyView()
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val spacingItemDecoration = SpacingItemDecoration(context)
        spacingItemDecoration.setVerticalSpacing(R.dimen.grid_spacing_half)
        return spacingItemDecoration
    }

    override fun setUpList() {
        super.setUpList()
        val padding = resources.getDimensionPixelSize(R.dimen.grid_spacing_quarter)
        baseListRecyclerView.itemAnimator = null
        baseListRecyclerView.setPadding(0, padding, 0, padding)
        hideBaseListLayout()
        mAdapter.onProductSelectedListener = productSelectedListener
    }

    override fun shouldIncludeSwipeToRefresh(): Boolean = false

    override fun performSearch(query: String?, querySubmitted: Boolean) {
        mPresenter.performSearch(query, querySubmitted)
    }

    override fun clearSearch() {
        mPresenter.clearSearch()
        baseListLayout.visibility = View.GONE
    }

    override fun showEmptySearchView() {
        super.showEmptySearchView()
        if (baseListEmptySearchLayout.childCount == 0) {
            LayoutInflater.from(context).inflate(R.layout.empty_search, baseListEmptySearchLayout, true)
        }
    }

    override fun showBaseListLayout() {
        AnimationHelper.animateView(baseListLayout, View.VISIBLE, UiConstants.ALPHA_OPAQUE, resources.getInteger(android.R.integer.config_shortAnimTime))
    }

    override fun hideBaseListLayout() {
        AnimationHelper.animateView(baseListLayout, View.GONE, UiConstants.ALPHA_TRANSPARENT, resources.getInteger(android.R.integer.config_shortAnimTime))
    }

    override fun isBaseListLayoutVisible(): Boolean {
        return baseListLayout.visibility == View.VISIBLE
    }

    override fun notifyItemChanged(position: Int) {
        mAdapter.notifyItemChanged(position)
    }

    override fun trackSearchedProductsAnalyticsEvent(query: String) {
        context?.let {
            AnalyticsHelper.trackEventWithDataSource(it, AnalyticsEvents.SEARCHED_PRODUCTS_EVENT, AnalyticsEvents.QUERY, query)
        }
    }

    interface Listener {
        fun onFragmentViewCreated()
        fun onFragmentViewDestroyed()
    }
}
