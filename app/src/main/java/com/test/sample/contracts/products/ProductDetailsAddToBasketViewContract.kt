package com.farmdrop.customer.contracts.products

import android.support.annotation.ColorRes
import uk.co.farmdrop.library.ui.base.MvpView

interface ProductDetailsAddToBasketViewContract {
    interface View : MvpView {
        fun setWholeViewClickListener()
        fun clearWholeViewClickListener()
        fun getBackgroundLevel(): Int
        fun setToBasketEmptyBackground()
        fun setToItemsInBasketBackground()
        fun showPlusMinusBasketButtons()
        fun hidePlusMinusBasketButtons()
        fun showAddToBasketText()
        fun hideAddToBasketText()
        fun setProductPriceTextColor(@ColorRes colorRes: Int)
        fun setProductPriceText(price: Double)
        fun setProductOldPriceTextColor(@ColorRes colorRes: Int)
        fun setProductOldPriceText(price: Double)
        fun setProductOldPriceStrikeThrough()
        fun showProductOldPriceText()
        fun hideProductOldPriceText()
        fun setTotalInBasketText(totalInBasket: String)
    }
}