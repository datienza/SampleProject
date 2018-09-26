package com.farmdrop.customer.utils.analytics

import android.support.annotation.IntDef

data class ProductViewLocation(@ProductLocation val location: Int, val sourceProperty: String, val pageProperty: String) {

    companion object {
        const val PRODUCT_TAXON = 1
        const val PRODUCT_SUBTAXON = 2
        const val PRODUCT_DETAIL = 3
        const val PRODUCT_TILE_PRODUCT_PAGE = 4
        const val PRODUCT_TILE_PRODUCER_PAGE = 5
        const val ORDER_DETAILS_PAGE = 6

        @IntDef(PRODUCT_TAXON, PRODUCT_SUBTAXON, PRODUCT_DETAIL, PRODUCT_TILE_PRODUCT_PAGE, PRODUCT_TILE_PRODUCER_PAGE, ORDER_DETAILS_PAGE)
        @Retention(AnnotationRetention.SOURCE)
        annotation class ProductLocation
    }
}
