package com.farmdrop.customer.ui.products

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.MotionEvent
import android.view.View
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.products.ProductsSearchActivityContract
import com.farmdrop.customer.presenters.products.ProductsSearchActivityPresenter
import com.farmdrop.customer.ui.common.BottomNavigationActivity
import kotlinx.android.synthetic.main.toolbar_search.searchView
import kotlinx.android.synthetic.main.toolbar_search.toolbar
import uk.co.farmdrop.library.di.activity.HasActivitySubcomponentBuilders
import uk.co.farmdrop.library.di.fragment.FragmentComponent
import uk.co.farmdrop.library.di.fragment.FragmentComponentBuilder
import uk.co.farmdrop.library.di.fragment.FragmentModule
import uk.co.farmdrop.library.di.fragment.HasFragmentSubcomponentBuilders
import uk.co.farmdrop.library.di.view.HasViewSubcomponentBuilders
import uk.co.farmdrop.library.di.view.ViewComponent
import uk.co.farmdrop.library.di.view.ViewComponentBuilder
import uk.co.farmdrop.library.di.view.ViewModule
import javax.inject.Inject
import javax.inject.Provider

class ProductsSearchActivity : BottomNavigationActivity<ProductsSearchActivityPresenter, ProductsSearchActivityContract.View>(),
    ProductsSearchActivityContract.View, HasFragmentSubcomponentBuilders, HasViewSubcomponentBuilders, ProductsSearchFragment.Listener, OnProductSelectedListener {

    @Inject
    lateinit var mFragmentComponentBuilders: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<FragmentComponentBuilder<*, *>>>

    @Inject
    lateinit var mViewComponentBuilders: Map<Class<out View>, @JvmSuppressWildcards Provider<ViewComponentBuilder<*, *>>>

    companion object {
        fun getStartingIntent(context: Context): Intent {
            return Intent(context, ProductsSearchActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_products)
        presenter.attachView(this)
        initToolbar()
        initBottomNavigation(R.id.action_products)
        initSearchView(searchView, false)
        initFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun injectMembers(hasActivitySubComponentBuilders: HasActivitySubcomponentBuilders) {
        (hasActivitySubComponentBuilders
            .getActivityComponentBuilder(ProductsSearchActivity::class.java) as ProductsSearchActivityComponent.Builder)
            .activityModule(ProductsSearchActivityComponent.ProductsSearchActivityModule(this))
            .build()
            .injectMembers(this)
    }

    override fun getFragmentComponentBuilder(fragmentClass: Class<out Fragment>?): FragmentComponentBuilder<out FragmentModule<*>, out FragmentComponent<*>> {
        return mFragmentComponentBuilders[fragmentClass]!!.get()
    }

    override fun getViewComponentBuilder(viewClass: Class<out View>): ViewComponentBuilder<out ViewModule<*>, out ViewComponent<*>>? {
        return mViewComponentBuilders[viewClass]!!.get()
    }

    override fun createFragment() {
        mCurrentFragment = ProductsSearchFragment.newInstance()
    }

    private fun initToolbar() {
        setToolbar(toolbar)
        // more stuff will be added
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_UP) {
            return presenter.onTouchUp()
        }
        return super.onTouchEvent(event)
    }

    override fun isSearchInInitialState(): Boolean {
        val currentFragment = mCurrentFragment
        return currentFragment is ProductsSearchFragment && !currentFragment.isBaseListLayoutVisible()
    }

    override fun onFragmentViewCreated() {
        initSearchViewListeners()
    }

    override fun onFragmentViewDestroyed() {
        clearSearchViewListeners()
    }

    override fun onProductSelected(selectedProduct: ProductData) {
        val intent = ProductDetailsActivity.getStartingIntent(this, graphQLProductId = selectedProduct.graphQLId)
        startActivity(intent)
    }
}
