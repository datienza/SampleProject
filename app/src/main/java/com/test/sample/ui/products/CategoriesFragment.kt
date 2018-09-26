package com.farmdrop.customer.ui.products

import android.content.Context
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.products.CategoriesFragmentContract
import com.farmdrop.customer.data.model.CategoryData
import com.farmdrop.customer.presenters.products.CategoriesFragmentPresenter
import com.farmdrop.customer.ui.adapter.products.CategoriesAdapter
import com.farmdrop.customer.ui.common.BaseListFragment
import kotlinx.android.synthetic.main.fragment_base_list.baseListEmptyResultTextView
import uk.co.farmdrop.library.di.fragment.HasFragmentSubcomponentBuilders
import uk.co.farmdrop.library.ui.base.FragmentSearchListener

class CategoriesFragment : BaseListFragment<CategoriesFragmentPresenter, CategoriesAdapter, CategoryData, CategoriesAdapter.CategoryViewHolder, CategoriesFragmentContract.View>(), CategoriesFragmentContract.View, FragmentSearchListener {

    companion object {
        fun newInstance() = CategoriesFragment()
    }

    private var onCategorySelectedListener: OnCategorySelectedListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.contentDescription = getString(R.string.content_description_fragment_categories)
        mPresenter.attachView(this)
        super.onViewCreated(view, savedInstanceState)
        loadDataForList(true)
    }

    override fun injectMembers(hasFragmentSubcomponentBuilders: HasFragmentSubcomponentBuilders) {
        (hasFragmentSubcomponentBuilders
                .getFragmentComponentBuilder(CategoriesFragment::class.java) as CategoriesFragmentComponent.Builder)
                .fragmentModule(CategoriesFragmentComponent.CategoriesFragmentModule(this))
                .build()
                .injectMembers(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnCategorySelectedListener) {
            onCategorySelectedListener = context
        } else {
            throw IllegalStateException(activity!!::class
                    .simpleName + " must implement " + OnCategorySelectedListener::class.java
                    .simpleName)
        }
    }

    override fun getItemDecoration(): RecyclerView.ItemDecoration {
        val itemDecoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.grey_line, null)?.let {
            itemDecoration.setDrawable(it)
        }
        return itemDecoration
    }

    override fun setUpList() {
        super.setUpList()
        mAdapter.onCategorySelectedListener = onCategorySelectedListener
    }

    override fun shouldIncludeSwipeToRefresh(): Boolean = false

    override fun showEmptyResultView() {
        super.showEmptySearchView()
        baseListEmptyResultTextView?.text = getString(R.string.search_no_category_found)
    }

    override fun onPause() {
        super.onPause()
        mPresenter.destroyAllDisposables()
    }
}