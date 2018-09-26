package com.farmdrop.customer.contracts.products

interface ProductsViewContract {

    interface View : BaseProductsListContract.View {
        fun hideViewOnEmptyResult()
    }
}