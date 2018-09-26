package com.farmdrop.customer.contracts.products

import com.farmdrop.customer.data.model.CategoryData
import uk.co.farmdrop.library.ui.base.BaseListContract

/**
 * Define contract methods between CategoriesFragment & CategoriesFragmentPresenter
 */
interface CategoriesFragmentContract {

    interface View : BaseListContract.BaseListView<CategoryData>
}
