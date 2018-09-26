package com.farmdrop.customer.ui.products

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.products.CategoryProductsActivityContract
import com.farmdrop.customer.data.model.CategoryData
import com.farmdrop.customer.data.model.ProductData
import com.farmdrop.customer.extensions.addFragment
import com.farmdrop.customer.extensions.setTabTextStyle
import com.farmdrop.customer.presenters.products.CategoryProductsActivityPresenter
import com.farmdrop.customer.ui.common.BottomNavigationActivity
import com.farmdrop.customer.ui.common.PagerAdapter
import com.farmdrop.customer.utils.analytics.AnalyticsEvents
import com.farmdrop.customer.utils.analytics.AnalyticsHelper
import kotlinx.android.synthetic.main.activity_category_products.contentLayout
import kotlinx.android.synthetic.main.toolbar_simple.toolbar
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

class CategoryProductsActivity : BottomNavigationActivity<CategoryProductsActivityPresenter, CategoryProductsActivityContract.View>(), CategoryProductsActivityContract.View,
        HasFragmentSubcomponentBuilders, HasViewSubcomponentBuilders, OnProductSelectedListener {

    companion object {
        private const val SAVED_INSTANCE_STATE_CURRENT_TAB = "saved_instance_state_current_tab"
        private const val CATEGORY_ID: String = "category_id"

        fun getStartingIntent(context: Context, categoryData: CategoryData) =
            Intent(context, CategoryProductsActivity::class.java).apply {
                putExtra(CATEGORY_ID, categoryData.id)
            }
    }

    @Inject
    lateinit var fragmentComponentBuilders: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<FragmentComponentBuilder<*, *>>>

    @Inject
    lateinit var viewComponentBuilders: Map<Class<out View>, @JvmSuppressWildcards Provider<ViewComponentBuilder<*, *>>>

    private var categoryId: Int? = null

    private var viewPager: ViewPager? = null

    private var pagerAdapter: PagerAdapter? = null

    private var tabLayout: TabLayout? = null

    private var tabSelectedListener: TabLayout.OnTabSelectedListener? = null

    private var savedInstanceStateCurrentTab: Int = 0

    private var tabLayoutOnPageChangeListener: TabLayout.TabLayoutOnPageChangeListener? = null

    private var viewPagerOnTabSelectedListener: TabLayout.ViewPagerOnTabSelectedListener? = null

    override fun injectMembers(hasActivitySubComponentBuilders: HasActivitySubcomponentBuilders) {
        (hasActivitySubComponentBuilders
                .getActivityComponentBuilder(CategoryProductsActivity::class.java) as CategoryProductsActivityComponent.Builder)
                .activityModule(CategoryProductsActivityComponent.CategoryProductsActivityModule(this, categoryId))
                .build()
                .injectMembers(this)
    }

    override fun getFragmentComponentBuilder(fragmentClass: Class<out Fragment>?): FragmentComponentBuilder<out FragmentModule<*>, out FragmentComponent<*>> =
        fragmentComponentBuilders[fragmentClass]!!.get()

    override fun getViewComponentBuilder(viewClass: Class<out View>): ViewComponentBuilder<out ViewModule<*>, out ViewComponent<*>>? =
        viewComponentBuilders[viewClass]!!.get()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_products)
        presenter.attachView(this)
        presenter.loadData()
        initToolbar()
        initBottomNavigation(R.id.action_products)
    }

    override fun retrieveIntentBundle(bundle: Bundle) {
        categoryId = bundle.getInt(CATEGORY_ID)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_category_products_activity, menu)
        val searchMenuItem = menu?.findItem(R.id.search)
        if (searchMenuItem != null) {
            initSearchView(searchMenuItem.actionView as SearchView, searchViewFromMenu = true)
            initSearchViewListeners()
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceStateCurrentTab = savedInstanceState?.getInt(SAVED_INSTANCE_STATE_CURRENT_TAB) ?: 0
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroyAllDisposables()
        removeListeners()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        savedInstanceStateCurrentTab = viewPager?.currentItem ?: 0
        outState?.putInt(SAVED_INSTANCE_STATE_CURRENT_TAB, savedInstanceStateCurrentTab)
    }

    override fun createFragment() {
        // not used.
    }

    private fun initToolbar() {
        setToolbar(toolbar)
        setHomeAsUpEnabled(R.drawable.ic_arrow_back_green)
    }

    override fun setToolbarTitle(title: String?) {
        toolbar.title = title
    }

    override fun getAllProductsTabTitle(): String = getString(R.string.all)

    override fun initViewPagerAndTabs() {
        val layoutInflater = LayoutInflater.from(this)
        val viewPagerLayout = layoutInflater.inflate(R.layout.products_view_pager, contentLayout, false)
        contentLayout.addView(viewPagerLayout)

        pagerAdapter = PagerAdapter(supportFragmentManager)

        val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position != null) {
                    tabLayout?.setTabTextStyle(tab.position, Typeface.BOLD)
                    presenter.onTabSelected(tab.position, tab.text?.toString())
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab?.position != null) {
                    tabLayout?.setTabTextStyle(tab.position, Typeface.NORMAL)
                }
            }
        }

        tabLayout = viewPagerLayout.findViewById(R.id.tabLayout)
        tabLayout?.tabMode = TabLayout.MODE_SCROLLABLE
        tabLayout?.addOnTabSelectedListener(tabSelectedListener)
        this.tabSelectedListener = tabSelectedListener

        viewPager = findViewById(R.id.viewPager)
    }

    override fun addTab(title: String, name: String) {
        pagerAdapter?.addFragment(ProductsFragment.newInstance(name), title)

        tabLayout?.newTab()?.apply {
            contentDescription = resources.getString(R.string.content_description_activity_category_products_tab)
            text = title
        }?.let {
            tabLayout?.addTab(it)
        }
    }

    override fun finalizeInitViewPagerAndTabs() {
        tabLayoutOnPageChangeListener = TabLayout.TabLayoutOnPageChangeListener(tabLayout).also {
            viewPager?.addOnPageChangeListener(it)
        }
        viewPagerOnTabSelectedListener = TabLayout.ViewPagerOnTabSelectedListener(viewPager).also {
            tabLayout?.addOnTabSelectedListener(it)
        }
        viewPager?.adapter = pagerAdapter
        viewPager?.currentItem = savedInstanceStateCurrentTab
    }

    override fun initProductsSingleFragment(name: String) {
        addFragment(ProductsFragment.newInstance(name), R.id.contentLayout)
    }

    override fun onProductSelected(selectedProduct: ProductData) {
        val intent = ProductDetailsActivity.getStartingIntent(this, graphQLProductId = selectedProduct.graphQLId)
        startActivity(intent)
    }

    override fun trackViewedSubcategoryEvent(subcategory: String) =
        AnalyticsHelper.trackEventWithDataSource(this, AnalyticsEvents.VIEWED_SUBCATEGORY_EVENT, AnalyticsEvents.NAME_PROPERTY, subcategory)

    private fun removeListeners() {
        tabLayoutOnPageChangeListener?.let {
            viewPager?.removeOnPageChangeListener(it)
        }
        viewPagerOnTabSelectedListener?.let {
            tabLayout?.removeOnTabSelectedListener(it)
        }
        tabSelectedListener?.let {
            tabLayout?.removeOnTabSelectedListener(it)
        }
    }
}
