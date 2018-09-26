package com.farmdrop.customer.contracts.products

import com.farmdrop.customer.data.model.nutrition.NutritionContents
import uk.co.farmdrop.library.ui.base.MvpView

interface ProductMoreInfoActivityContract {

    interface View : MvpView {
        fun displayMoreInfoText(moreInfoText: String?)
        fun getSingleNutrientLayoutBottomPadding(): Int
        fun getDoubleNutrientLayoutBottomPadding(): Int
        fun displayShelfLife(shelfLifeDays: String?)
        fun initNutritionRecyclerView()
        fun setNutritionContents(nutritionContent: List<NutritionContents>)
    }
}