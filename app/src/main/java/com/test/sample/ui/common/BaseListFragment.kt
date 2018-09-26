package com.farmdrop.customer.ui.common

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farmdrop.customer.R
import com.farmdrop.customer.utils.constants.UiConstants
import kotlinx.android.synthetic.main.fragment_base_list.baseListEmptyResultTextView
import kotlinx.android.synthetic.main.fragment_base_list.baseListEmptySearchLayout
import kotlinx.android.synthetic.main.fragment_base_list.baseListProgressBar
import kotlinx.android.synthetic.main.fragment_base_list.baseListRecyclerView
import kotlinx.android.synthetic.main.fragment_base_list.baseListSwipeRefreshLayout
import uk.co.farmdrop.library.ui.SpacingItemDecoration
import uk.co.farmdrop.library.ui.base.BaseAdapter
import uk.co.farmdrop.library.ui.base.BaseListContract
import uk.co.farmdrop.library.ui.base.BaseListPresenter
import uk.co.farmdrop.library.ui.base.FragmentSearchListener
import uk.co.farmdrop.library.ui.helper.AnimationHelper
import uk.co.farmdrop.library.utils.KeyboardUtils
import javax.inject.Inject

abstract class BaseListFragment<T : BaseListPresenter<J, L>, A : BaseAdapter<J, H>, J, H : RecyclerView.ViewHolder?, L : BaseListContract.BaseListView<J>> : BaseFragment(), BaseListContract.BaseListView<J>, FragmentSearchListener {

    @Inject
    protected lateinit var mPresenter: T

    @Inject
    protected lateinit var mAdapter: A

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.showLoadingOnStart()
        setUpList()
        setUpSwipeToRefresh()
        baseListRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                KeyboardUtils.hideKeyboard(context, view)
            }
        })
    }

    protected open fun shouldIncludeSwipeToRefresh(): Boolean = true

    protected open fun setUpSwipeToRefresh() {
        if (shouldIncludeSwipeToRefresh()) {
            baseListSwipeRefreshLayout.setColorSchemeResources(R.color.farmdropGreen, R.color.savoyCabbage, R.color.farmdropPurple, R.color.lightPurple)
            baseListSwipeRefreshLayout.setOnRefreshListener(getRefreshListener())
        } else {
            baseListSwipeRefreshLayout?.isEnabled = false
        }
    }

    @LayoutRes
    protected open fun getLayoutRes(): Int {
        return R.layout.fragment_base_list
    }

    private fun getRefreshListener(): SwipeRefreshLayout.OnRefreshListener {
        return SwipeRefreshLayout.OnRefreshListener {
            baseListSwipeRefreshLayout.isRefreshing = true
            mPresenter.loadData(true)
        }
    }

    protected fun loadDataForList(forceReload: Boolean) {
        mPresenter.loadData(forceReload)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.destroyAllDisposables()
    }

    override fun onDetach() {
        super.onDetach()
        mPresenter.detachView()
    }

    @CallSuper
    protected open fun setUpList() {
        baseListRecyclerView.layoutManager = getLayoutManager()
        baseListRecyclerView.adapter = mAdapter
        getItemDecoration()?.let {
            baseListRecyclerView.addItemDecoration(it)
        }
        baseListRecyclerView.setHasFixedSize(true)
    }

    protected open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    protected open fun getItemDecoration(): RecyclerView.ItemDecoration? {
        val verticalSpaceItemDecoration = SpacingItemDecoration(context)
        verticalSpaceItemDecoration.setVerticalSpacing(R.dimen.grid_spacing_half)
        return verticalSpaceItemDecoration
    }

    override fun hideSwipeRefreshingView() {
        baseListSwipeRefreshLayout.isRefreshing = false
    }

    override fun showEmptyResultView() {
        baseListEmptyResultTextView.visibility = View.VISIBLE
    }

    override fun hideEmptyResultView() {
        baseListEmptyResultTextView.visibility = View.GONE
    }

    override fun showEmptySearchView() {
        if (baseListEmptySearchLayout != null) {
            baseListEmptySearchLayout.visibility = View.VISIBLE
        }
    }

    override fun hideEmptySearchView() {
        if (baseListEmptySearchLayout != null) {
            baseListEmptySearchLayout.visibility = View.GONE
        }
    }

    override fun showProgressBar() {
        if (baseListProgressBar != null) {
            baseListProgressBar.alpha = UiConstants.ALPHA_OPAQUE
            baseListProgressBar.visibility = View.VISIBLE
        }
    }

    override fun hideProgressBar() {
        if (baseListProgressBar != null && baseListProgressBar.visibility == View.VISIBLE) {
            AnimationHelper.animateView(baseListProgressBar, View.GONE, UiConstants.ALPHA_TRANSPARENT, resources.getInteger(android.R.integer.config_shortAnimTime))
        }
    }

    override fun onFilteredSearch(filteredSearch: String) {
        mPresenter.loadDataForFilteredSearch(filteredSearch)
        baseListSwipeRefreshLayout.isEnabled = TextUtils.isEmpty(filteredSearch)
    }

    override fun populateList(data: List<J>) {
        if (baseListProgressBar != null && baseListRecyclerView.visibility != View.VISIBLE) {
            // The animation will be executed when the recyclerview is inflated, because it doesn't seem to visible randomly
            baseListRecyclerView.post {
                if (isAdded) {
                    AnimationHelper.crossFadeAnimation(baseListProgressBar, baseListRecyclerView, resources.getInteger(android.R.integer.config_shortAnimTime))
                }
            }
        }

        mAdapter.setData(data)
        mAdapter.notifyDataSetChanged()
    }

    override fun hideList() {
        baseListRecyclerView.visibility = View.GONE
    }
}
