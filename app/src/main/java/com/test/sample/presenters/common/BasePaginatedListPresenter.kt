package com.farmdrop.customer.presenters.common

import android.support.annotation.CallSuper
import com.farmdrop.customer.data.model.pagination.PaginatedData
import uk.co.farmdrop.library.ui.base.BaseListContract
import uk.co.farmdrop.library.ui.base.BaseListPresenter

abstract class BasePaginatedListPresenter<D, V : BaseListContract.BaseListView<D>, P : PaginatedData<D>> : BaseListPresenter<D, V>() {

    var isLoading = false

    var hasLoadedAllItems = false

    var paginatedData: P? = null

    fun loadMoreData() {
        // if paginatedData == null, means no data has been loaded yet
        if (!isLoading && paginatedData != null) {
            loadData(true, paginatedData)
        }
    }

    @CallSuper
    open fun loadData(forceLoad: Boolean, previousPaginatedData: P? = null) {
        isLoading = true
    }

    open fun onPaginatedDataLoaded(paginatedData: P) {
        this.paginatedData = paginatedData

        isLoading = false
        hasLoadedAllItems = paginatedData.hasLoadedAllItems()
    }
}