package com.farmdrop.customer.ui.common

import android.graphics.Typeface
import android.support.annotation.DrawableRes
import android.support.v7.widget.SearchView
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.common.BaseSearchActivityContract
import uk.co.farmdrop.library.extensions.getColour
import com.farmdrop.customer.extensions.setIcon
import com.farmdrop.customer.extensions.setTextMargin
import com.farmdrop.customer.extensions.setTextStyle
import com.farmdrop.customer.presenters.common.BaseSearchActivityPresenter
import com.farmdrop.customer.ui.products.ProductsSearchActivity
import com.farmdrop.customer.utils.analytics.AnalyticsEvents
import com.farmdrop.customer.utils.analytics.AnalyticsHelper
import javax.inject.Inject

abstract class BaseSearchActivity<P : BaseSearchActivityPresenter<V>, V : BaseSearchActivityContract.View> : BaseActivity(), BaseSearchActivityContract.View {

    @Inject
    lateinit var presenter: P

    private var searchView: SearchView? = null

    private val onQueryTextFocusChangeListener = View.OnFocusChangeListener { _, hasFocus -> presenter.onSearchViewFocusChanged(hasFocus) }

    protected fun initSearchView(searchView: SearchView, openNewActivityOnFocus: Boolean = true, searchViewFromMenu: Boolean = false) {
        this.searchView = searchView
        presenter.openNewActivityOnFocus = openNewActivityOnFocus
        presenter.searchViewFromMenu = searchViewFromMenu

        searchView.setTextStyle(Typeface.BOLD)
        val searchViewMargin = resources.getDimensionPixelSize(if (searchViewFromMenu) R.dimen.search_icon_from_menu_margin else R.dimen.search_icon_margin)
        searchView.setTextMargin(searchViewMargin, 0, 0, 0)

        val searchViewEditText = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text) as EditText
        searchViewEditText.setTextColor(getColour(R.color.farmdropBlack))
        searchViewEditText.setHintTextColor(getColour(R.color.grey4))
        searchViewEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.head2))
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
        initSearchViewListeners()
    }

    override fun onPause() {
        super.onPause()
        clearSearchViewListeners()
    }

    protected fun initSearchViewListeners() {
        searchView?.setOnQueryTextFocusChangeListener(onQueryTextFocusChangeListener)

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView?.clearFocus()
                performSearch(query, true)
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                performSearch(query, false)
                return true
            }
        })

        searchView?.setOnCloseListener {
            clearSearch()
        }
    }

    protected fun clearSearchViewListeners() {
        searchView?.setOnQueryTextFocusChangeListener(null)
        searchView?.setOnQueryTextListener(null)
        searchView?.setOnCloseListener(null)
    }

    override fun clearSearchViewFocus() {
        searchView?.setOnQueryTextFocusChangeListener(null)
        searchView?.clearFocus()
        searchView?.setOnQueryTextFocusChangeListener(onQueryTextFocusChangeListener)
        searchView?.isFocusable = false
    }

    override fun collapseSearchView() {
        searchView?.isIconified = true
    }

    override fun openSearchActivity() {
        startActivity(ProductsSearchActivity.getStartingIntent(this))
    }

    override fun updateSearchViewIcon(@DrawableRes icon: Int) {
        searchView?.setIcon(icon)
    }

    private fun performSearch(query: String?, querySubmitted: Boolean) {
        val currentFragment = mCurrentFragment
        if (currentFragment is Searchable) {
            currentFragment.performSearch(query, querySubmitted)
        }
    }

    private fun clearSearch(): Boolean {
        val currentFragment = mCurrentFragment
        if (currentFragment is Searchable) {
            currentFragment.clearSearch()
            return true
        }
        return false
    }

    override fun trackTappedSearchAnalyticsEvent() {
        AnalyticsHelper.trackEventWithDataSource(this, AnalyticsEvents.TAPPED_SEARCH_EVENT)
    }
}