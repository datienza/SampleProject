package com.farmdrop.customer.contracts.common

import android.support.annotation.DrawableRes
import uk.co.farmdrop.library.ui.base.MvpView

interface BaseSearchActivityContract {

    interface View : MvpView {
        fun clearSearchViewFocus()
        fun collapseSearchView()

        fun openSearchActivity()
        fun updateSearchViewIcon(@DrawableRes icon: Int)

        fun trackTappedSearchAnalyticsEvent()
    }
}
