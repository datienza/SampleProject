package com.farmdrop.customer.contracts.products

import android.support.annotation.DrawableRes
import com.segment.analytics.Properties
import uk.co.farmdrop.library.ui.base.MvpView

/**
 * Define contract methods between ProductView & ProductViewPresenter
 */
interface BaseProductContract {

    interface View : MvpView {
        fun displayProductName(name: String)
        fun displayProducerName(name: String)
        fun displayProductPicture(pictureUrl: String)
        fun displayDefaultProductPicture(@DrawableRes placeHolder: Int)
        fun displayPrice(price: Double)

        fun displayVariantSingleText(variant: String)
        fun hideVariantSingleText()

        fun initVariantsRadioGroup()
        fun addVariantRadioButton(variant: String)
        fun hideVariantsRadioGroup()
        fun selectVariantRadioButton(index: Int)

        fun displayVariantsDropDown(variants: List<String?>)
        fun hideVariantsDropDown()
        fun selectVariantDropDown(index: Int)

        fun createVariantText(unit: String, value: Double, valueType: String): String
        fun createVariantText(value: Double, valueType: String): String
        fun showMarketingTag(name: String)
        fun hideMarketingTag()
        fun hideAddToBasketButton()
        fun showAddToBasketButton()
        fun updateBasketItemsNumber(number: Int)
        fun trackAddedProduct(properties: Properties)
        fun trackRemovedProduct(properties: Properties)

        fun showQuantityRemaining(quantity: Int)
        fun hideQuantityRemaining()
        fun showItemsSoldOut()
        fun hideItemsSoldOut()
        fun enableAddToBasketMultipleItemButton()
        fun disableAddToBasketMultipleItemButton()
    }
}
