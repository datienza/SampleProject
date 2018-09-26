package com.farmdrop.customer.presenters.products

import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.products.ProductDetailsAddToBasketViewContract
import com.farmdrop.customer.ui.products.ProductDetailsAddToBasketView.Companion.EMPTY_BASKET_BACKGROUND_LAYER
import com.farmdrop.customer.ui.products.ProductDetailsAddToBasketView.Companion.POPULATED_BASKET_BACKGROUND_LAYER
import uk.co.farmdrop.library.ui.base.BasePresenter

class ProductDetailsAddToBasketViewPresenter : BasePresenter<ProductDetailsAddToBasketViewContract.View>() {

    internal var isDiscountedPrice = false

    fun displayPrice(price: Double) {
        isDiscountedPrice = false
        mvpView.setProductPriceText(price)
        mvpView.hideProductOldPriceText()
    }

    fun displayDiscountedPrice(price: Double, oldPrice: Double) {
        isDiscountedPrice = true
        mvpView.setProductPriceText(price)
        mvpView.setProductOldPriceStrikeThrough()
        mvpView.setProductOldPriceText(oldPrice)
        mvpView.showProductOldPriceText()

        val buttonStatus = mvpView.getBackgroundLevel()
        when (buttonStatus) {
            EMPTY_BASKET_BACKGROUND_LAYER -> {
                setPriceColorsAllWhite()
            }
            POPULATED_BASKET_BACKGROUND_LAYER -> {
                setPriceColorsDiscountedPrice()
            }
        }
    }

    fun setTotalInBasket(totalToDisplay: Int) {
        mvpView.setTotalInBasketText(totalToDisplay.toString())
    }

    fun displayAsZeroInBasket() {
        mvpView.hidePlusMinusBasketButtons()
        mvpView.showAddToBasketText()
        mvpView.setWholeViewClickListener()
        mvpView.setToBasketEmptyBackground()
        setPriceColorsAllWhite()
    }

    fun displayAsItemsInBasket() {
        mvpView.showPlusMinusBasketButtons()
        mvpView.hideAddToBasketText()
        mvpView.setToItemsInBasketBackground()
        mvpView.clearWholeViewClickListener()
        if (isDiscountedPrice) setPriceColorsDiscountedPrice() else setPriceColorsNonDiscountedPrice()
    }

    private fun setPriceColorsAllWhite() {
        mvpView.setProductPriceTextColor(R.color.white)
        mvpView.setProductOldPriceTextColor(R.color.white)
    }

    private fun setPriceColorsDiscountedPrice() {
        mvpView.setProductPriceTextColor(R.color.warningTomato)
        mvpView.setProductOldPriceTextColor(R.color.farmdropBlack)
    }

    private fun setPriceColorsNonDiscountedPrice() {
        mvpView.setProductPriceTextColor(R.color.farmdropBlack)
    }
}