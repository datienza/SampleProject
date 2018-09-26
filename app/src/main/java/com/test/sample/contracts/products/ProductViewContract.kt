package com.farmdrop.customer.contracts.products

/**
 * Define contract methods between ProductView & ProductViewPresenter
 */
interface ProductViewContract {

    interface View : BaseProductContract.View {

        fun hideBasketMultipleItemsLayout()
        fun showBasketMultipleItemsLayout()

        fun hideProductImageOverlay()
        fun showProductImageOverlay()
        fun updateProductImageOverlayItemsNumber(number: Int)

        fun displaySalePrice(price: Double)
        fun displayOldPrice(oldPrice: Double)
        fun displayPricePerUnit(pricePerUnit: String)
        fun hideSecondaryPrice()
    }
}
