package com.farmdrop.customer.contracts.basket

import uk.co.farmdrop.library.ui.base.MvpView

interface BasketItemViewContract {

    interface View : MvpView {
        fun updateBasketItemsNumber(number: Int)
        fun displayProductName(name: String)
        fun displayProductPicture(pictureUrl: String)
        fun displayDefaultProductPicture(placeHolder: Int)
        fun displayPrice(price: Float)
        fun displayVariantText(variant: String)
        fun hideAddRemoveBasketMultipleItemsLayout()
        fun displayQuantityOverlay(quantity: Int)
        fun showItemsSoldOut()
        fun hideItemsSoldOut()
    }
}
