package com.farmdrop.customer.presenters.common

import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.common.BaseSearchActivityContract
import com.test.sample.presenters.common.BasePresenter
import uk.co.farmdrop.library.ui.base.BasePresenter

abstract class BaseSearchActivityPresenter<V : BaseSearchActivityContract.View> : BasePresenter<V>() {

    var openNewActivityOnFocus = false

    var searchViewFromMenu = false

    open fun onResume() {
        // if this activity is not directly responsible for searching, prevent the keyboard from showing off by default
        if (openNewActivityOnFocus) {
            mvpView?.clearSearchViewFocus()
        }
    }

    fun onSearchViewFocusChanged(isFocused: Boolean) {
        if (isFocused) {
            if (openNewActivityOnFocus) {
                mvpView?.trackTappedSearchAnalyticsEvent()
                mvpView?.clearSearchViewFocus()
                if (searchViewFromMenu) {
                    mvpView?.collapseSearchView()
                }
                mvpView?.updateSearchViewIcon(R.drawable.ic_search_green)
                mvpView?.openSearchActivity()
            } else {
                mvpView?.updateSearchViewIcon(R.drawable.ic_search_grey)
            }
        } else {
            mvpView?.updateSearchViewIcon(R.drawable.ic_search_green)
        }
    }
}
