package com.farmdrop.customer.utils.analytics

object ProductViewLocationFactory {

    fun getProductViewLocation(@ProductViewLocation.Companion.ProductLocation location: Int) =
        when (location) {
            ProductViewLocation.PRODUCT_TAXON -> ProductViewLocation(location, AnalyticsEvents.PRODUCT_TILE_VALUE, AnalyticsEvents.TAXON_VALUE)
            ProductViewLocation.PRODUCT_SUBTAXON -> ProductViewLocation(location, AnalyticsEvents.PRODUCT_TILE_VALUE, AnalyticsEvents.SUBTAXON_VALUE)
            ProductViewLocation.PRODUCT_DETAIL -> ProductViewLocation(location, AnalyticsEvents.PRODUCT_DETAIL_VALUE, AnalyticsEvents.PRODUCT_DETAIL_VALUE)
            ProductViewLocation.PRODUCT_TILE_PRODUCT_PAGE -> ProductViewLocation(location, AnalyticsEvents.PRODUCT_TILE_PRODUCT_PAGE_VALUE, AnalyticsEvents.PRODUCT_DETAIL_VALUE)
            ProductViewLocation.PRODUCT_TILE_PRODUCER_PAGE -> ProductViewLocation(location, AnalyticsEvents.PRODUCT_TILE_PRODUCER_PAGE_VALUE, AnalyticsEvents.PRODUCER_DETAIL_VALUE)
            ProductViewLocation.ORDER_DETAILS_PAGE -> ProductViewLocation(location, AnalyticsEvents.PRODUCT_TILE_ORDER_DETAILS_PAGE_VALUE, AnalyticsEvents.PAST_ORDER_VALUE)
            else -> ProductViewLocation(location, AnalyticsEvents.PRODUCT_TILE_VALUE, AnalyticsEvents.PRODUCT_TILE_VALUE)
        }
}
