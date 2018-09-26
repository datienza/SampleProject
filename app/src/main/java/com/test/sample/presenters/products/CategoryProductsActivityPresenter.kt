package com.farmdrop.customer.presenters.products

import com.farmdrop.customer.contracts.products.CategoryProductsActivityContract
import com.farmdrop.customer.data.model.CategoryData
import com.farmdrop.customer.data.repository.CategoriesRepository
import com.farmdrop.customer.managers.BasketManager
import com.farmdrop.customer.presenters.common.BottomNavigationActivityPresenter
import com.farmdrop.customer.utils.BasketRxBus
import io.reactivex.Scheduler
import timber.log.Timber
import uk.co.farmdrop.library.utils.Constants.ZERO

class CategoryProductsActivityPresenter(
    private val categoriesRepository: CategoriesRepository,
    private val categoryId: Int?,
    bottomNavigationActivityTabsManager: BaseBottomNavigationActivityTabsManager,
    basketRxBus: BasketRxBus,
    basketManager: BasketManager,
    uiScheduler: Scheduler
) : BottomNavigationActivityPresenter<CategoryProductsActivityContract.View>(bottomNavigationActivityTabsManager, basketRxBus, basketManager, uiScheduler) {

    internal var categoryData: CategoryData? = null

    fun loadData() {
        if (categoryId != null) {
            addDisposable(categoriesRepository.getCategory(categoryId)
                    ?.subscribe({ categoryData: CategoryData ->
                        this.categoryData = categoryData
                        mvpView?.setToolbarTitle(categoryData.name)
                        val categoryPermalink = categoryData.permalink

                        if (categoryPermalink != null) {
                            val categoryName = categoryData.name
                            val subCategories = categoryData.children
                            if (subCategories != null && subCategories.size > 1) {
                                mvpView?.initViewPagerAndTabs()
                                if (categoryName != null) {
                                    mvpView?.addTab(mvpView.getAllProductsTabTitle(), categoryName)
                                }
                                for (subCategory in subCategories) {
                                    val subCategoryName = subCategory.name
                                    val subCategoryPermalink = subCategory.permalink
                                    if (subCategoryName != null && subCategoryPermalink != null) {
                                        mvpView?.addTab(subCategoryName, subCategoryName)
                                    }
                                }
                                mvpView?.finalizeInitViewPagerAndTabs()
                            } else if (categoryName != null) {
                                mvpView?.initProductsSingleFragment(categoryName)
                            }
                        }
                    }, {
                        Timber.e(it)
                    }))
        }
    }

    fun onTabSelected(position: Int, text: String?) {
        categoryData?.let {
            val subcategory = if (position == ZERO) {
                it.name
            } else {
                text
            }
            if (subcategory != null) {
                mvpView?.trackViewedSubcategoryEvent(subcategory)
            }
        }
    }
}
