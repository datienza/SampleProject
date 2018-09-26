package com.farmdrop.customer.ui.products

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.View
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.products.ProductsActivityContract
import com.farmdrop.customer.data.model.CategoryData
import com.farmdrop.customer.data.model.ProducerData
import com.farmdrop.customer.extensions.setTabTextStyle
import com.farmdrop.customer.presenters.products.ProductsActivityPresenter
import com.farmdrop.customer.ui.common.BottomNavigationActivity
import com.farmdrop.customer.ui.common.PagerAdapter
import com.farmdrop.customer.ui.producers.OnProducerSelectedListener
import com.farmdrop.customer.ui.producers.ProducerDetailsActivity
import com.farmdrop.customer.ui.producers.ProducersFragment
import com.farmdrop.customer.utils.analytics.AnalyticsEvents
import com.farmdrop.customer.utils.analytics.AnalyticsHelper
import kotlinx.android.synthetic.main.activity_products.tabLayout
import kotlinx.android.synthetic.main.activity_products.viewPager
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

class ProductsActivity : BottomNavigationActivity<ProductsActivityPresenter, ProductsActivityContract.View>(),
        HasFragmentSubcomponentBuilders, HasViewSubcomponentBuilders, ProductsActivityContract.View, OnCategorySelectedListener, OnProducerSelectedListener {

    @Inject
    lateinit var fragmentComponentBuilders: Map<Class<out Fragment>, @JvmSuppressWildcards Provider<FragmentComponentBuilder<*, *>>>

    @Inject
    lateinit var mViewComponentBuilders: Map<Class<out View>, @JvmSuppressWildcards Provider<ViewComponentBuilder<*, *>>>

    private var mTabSelectedListener: TabLayout.OnTabSelectedListener? = null

    companion object {
        const val PRODUCTS_TAB = 0
        const val PRODUCERS_TAB = 1

        fun getStartingIntent(context: Context): Intent {
            return Intent(context, ProductsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)
        presenter.attachView(this)
        initToolbar()
        initViewPagerAndTabs()
        initBottomNavigation(R.id.action_products)
        initSearchView(searchView)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        val tabSelectedListener = mTabSelectedListener
        if (tabSelectedListener != null) {
            tabLayout.removeOnTabSelectedListener(tabSelectedListener)
        }
    }

    override fun injectMembers(hasActivitySubComponentBuilders: HasActivitySubcomponentBuilders) {
        (hasActivitySubComponentBuilders
                .getActivityComponentBuilder(ProductsActivity::class.java) as ProductsActivityComponent.Builder)
                .activityModule(ProductsActivityComponent.ProductsActivityModule(this))
                .build()
                .injectMembers(this)
    }

    override fun getFragmentComponentBuilder(fragmentClass: Class<out Fragment>): FragmentComponentBuilder<out FragmentModule<*>, out FragmentComponent<*>> =
            fragmentComponentBuilders[fragmentClass]!!.get()

    override fun getViewComponentBuilder(viewClass: Class<out View>?): ViewComponentBuilder<out ViewModule<*>, out ViewComponent<*>> =
            mViewComponentBuilders[viewClass]!!.get()

    override fun createFragment() {
        // nothing to do
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    private fun initToolbar() {
        setToolbar(toolbar)
        // more stuff will be added
    }

    private fun initViewPagerAndTabs() {
        val pagerAdapter = PagerAdapter(supportFragmentManager)
        val categoriesFragment = getCategoriesFragment()
        val producersFragment = getProducersFragment()
        pagerAdapter.addFragment(categoriesFragment, getString(R.string.products))
        pagerAdapter.addFragment(producersFragment, getString(R.string.producers))
        viewPager.adapter = pagerAdapter

        val tabSelectedListener = object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position != null) {
                    tabLayout?.setTabTextStyle(tab.position, Typeface.BOLD)
                    presenter.onTabSelected(tab.position)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                if (tab?.position != null) {
                    tabLayout?.setTabTextStyle(tab.position, Typeface.NORMAL)
                }
            }
        }
        tabLayout.addOnTabSelectedListener(tabSelectedListener)
        mTabSelectedListener = tabSelectedListener

        tabLayout.setupWithViewPager(viewPager)
    }

    private fun getCategoriesFragment(): Fragment {
        return CategoriesFragment.newInstance()
    }

    private fun getProducersFragment(): Fragment {
        return ProducersFragment.newInstance()
    }

    override fun onCategorySelected(selectedCategory: CategoryData) {
        selectedCategory.name?.let {
            AnalyticsHelper.trackEventWithDataSource(this, AnalyticsEvents.VIEWED_CATEGORY_EVENT,
                    AnalyticsEvents.NAME_PROPERTY, it)
        }
        openNewActivityForCurrentTab(CategoryProductsActivity.getStartingIntent(this, selectedCategory))
    }

    override fun onProducerSelected(selectedProducer: ProducerData) {
        selectedProducer.name?.let {
            AnalyticsHelper.trackEventWithDataSource(this, AnalyticsEvents.VIEWED_PRODUCER_DETAIL_EVENT,
                    AnalyticsEvents.PRODUCER_NAME_PROPERTY, it, AnalyticsEvents.SOURCE_PROPERTY, AnalyticsEvents.PRODUCER_TAB_VALUE)
        }
        val intent = ProducerDetailsActivity.getStartingIntent(this, selectedProducer.id)
        startActivity(intent)
    }

    override fun trackTabSelectEvent(title: String) {
        AnalyticsHelper.trackEventWithDataSource(this, AnalyticsEvents.CHANGED_SECTION_EVENT,
                AnalyticsEvents.NAME_PROPERTY, title)
    }
}
