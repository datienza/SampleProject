package com.farmdrop.customer.ui.common

import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import com.farmdrop.customer.data.model.pagination.PaginatedData
import com.farmdrop.customer.presenters.common.BasePaginatedListPresenter
import com.paginate.Paginate
import kotlinx.android.synthetic.main.fragment_base_list.baseListRecyclerView
import uk.co.farmdrop.library.ui.base.BaseAdapter
import uk.co.farmdrop.library.ui.base.BaseListContract

abstract class BasePaginatedListFragment<T : BasePaginatedListPresenter<D, L, P>, A : BaseAdapter<D, H>, D, H : RecyclerView.ViewHolder?, L : BaseListContract.BaseListView<D>, P : PaginatedData<D>> :
        BaseListFragment<T, A, D, H, L>() {

    companion object {
        const val LOADING_TRIGGER_THRESHOLD = 2
    }

    private var mPaginate: Paginate? = null

    // child classes can set a different threshold if needed. Needs to be set before onViewCreated is called
    protected var mLoadingTriggerThreshold = LOADING_TRIGGER_THRESHOLD

    @CallSuper
    override fun setUpList() {
        super.setUpList()

        val callbacks = object : Paginate.Callbacks {
            override fun onLoadMore() {
                mPresenter.loadMoreData()
            }

            override fun isLoading() = mPresenter.isLoading

            override fun hasLoadedAllItems() = mPresenter.hasLoadedAllItems
        }

        mPaginate = Paginate.with(baseListRecyclerView, callbacks)
                .setLoadingTriggerThreshold(mLoadingTriggerThreshold)
                .addLoadingListItem(true)
                .build()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPaginate?.unbind()
    }
}