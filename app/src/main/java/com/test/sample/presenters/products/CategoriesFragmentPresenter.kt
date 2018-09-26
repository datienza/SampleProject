package com.farmdrop.customer.presenters.products

import com.farmdrop.customer.contracts.products.CategoriesFragmentContract
import com.farmdrop.customer.data.model.CategoryData
import com.farmdrop.customer.data.repository.CategoriesRepository
import io.reactivex.functions.Consumer
import io.realm.RealmResults
import uk.co.farmdrop.library.resolution.ErrorResolutionConsumer
import uk.co.farmdrop.library.ui.base.BaseListPresenter

/**
 * Handle CategoriesFragment logic.
 * Created by datienza on 14/08/2017.
 */
class CategoriesFragmentPresenter(private val mCategoriesRepository: CategoriesRepository) : BaseListPresenter<CategoryData, CategoriesFragmentContract.View>() {

    override fun loadData(forceReload: Boolean) {
        addDisposable(mCategoriesRepository.getCategories(forceReload)
            ?.subscribe(Consumer { categoriesList: RealmResults<CategoryData> ->
                hideSwipeRefreshingView(forceReload)
                displayData(categoriesList)
            },
            ErrorResolutionConsumer(null
            ) {
                mvpView.hideProgressBar()
                hideSwipeRefreshingView(forceReload)
            }))
    }
}