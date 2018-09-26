package com.farmdrop.customer.ui.common

import android.content.Context
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import com.farmdrop.customer.R
import com.farmdrop.customer.utils.constants.UiConstants
import kotlinx.android.synthetic.main.view_base_list.view.baseListEmptyResultTextView
import kotlinx.android.synthetic.main.view_base_list.view.baseListEmptySearchLayout
import kotlinx.android.synthetic.main.view_base_list.view.baseListProgressBar
import kotlinx.android.synthetic.main.view_base_list.view.baseListRecyclerView
import uk.co.farmdrop.library.ui.SpacingItemDecoration
import uk.co.farmdrop.library.ui.base.BaseAdapter
import uk.co.farmdrop.library.ui.base.BaseListContract
import uk.co.farmdrop.library.ui.base.BaseListPresenter
import uk.co.farmdrop.library.ui.helper.AnimationHelper
import javax.inject.Inject

abstract class BaseListView<T : BaseListPresenter<J, L>, A : BaseAdapter<J, H>, J, H : RecyclerView.ViewHolder?, L : BaseListContract.BaseListView<J>>(context: Context) : FrameLayout(context), BaseListContract.BaseListView<J> {

    @Inject
    protected lateinit var mPresenter: T

    @Inject
    protected lateinit var mAdapter: A

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        mPresenter.showLoadingOnStart()
        setUpList()
    }

    @LayoutRes
    protected open fun getLayoutRes(): Int {
        return R.layout.view_base_list
    }

    protected fun loadDataForList(forceReload: Boolean) {
        mPresenter.loadData(forceReload)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mPresenter.destroyAllDisposables()
        mPresenter.detachView()
    }

    @CallSuper
    protected open fun setUpList() {
        baseListRecyclerView.layoutManager = getLayoutManager()
        baseListRecyclerView.adapter = mAdapter
        baseListRecyclerView.addItemDecoration(getItemDecoration())
        baseListRecyclerView.setHasFixedSize(true)
    }

    protected open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, 1, false)
    }

    protected open fun getItemDecoration(): RecyclerView.ItemDecoration {
        val verticalSpaceItemDecoration = SpacingItemDecoration(context)
        verticalSpaceItemDecoration.setVerticalSpacing(R.dimen.grid_spacing_half)
        return verticalSpaceItemDecoration
    }

    override fun hideSwipeRefreshingView() {
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

    override fun populateList(data: List<J>) {
        if (baseListProgressBar != null && baseListRecyclerView.visibility != View.VISIBLE) {
            // The animation will be executed when the recyclerview is inflated, because it doesn't seem to visible randomly
            baseListRecyclerView.post {
                if (ViewCompat.isAttachedToWindow(this) ) {
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