package com.farmdrop.customer.contracts.products

interface OrderProductViewContract {

    interface View : ProductViewContract.View {
        fun showItemAsUnavailable()
        fun showAddRemoveButtons()
        fun hideAddRemoveButtons()
    }
}
