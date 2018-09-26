package com.farmdrop.customer.utils.analytics

object AnalyticsEvents {

    // global properties
    const val DATA_SOURCE_PROPERTY = "fd_data_source"
    const val DATA_SOURCE_VALUE = "Android app"

    // App started event
    const val APPLICATION_STARTED = "Application started"

    // Properties
    const val SECTION_PROPERTY = "section"
    const val PRODUCER_NAME_PROPERTY = "producer name"
    const val TITLE_PROPERTY = "title"
    const val NAME_PROPERTY = "name"
    const val ORDER_TYPE_PROPERTY = "order type"
    const val SOURCE_PROPERTY = "source"
    const val PRODUCT_SOURCE_PROPERTY = "product source"
    const val FREQUENCY_PROPERTY = "frequency"
    const val PAGE_PROPERTY = "page"
    const val PRODUCER_PROPERTY = "producer"
    const val PRICE_PROPERTY = "price"
    const val ID_PROPERTY = "id"
    const val PRODUCER_ID_PROPERTY = "producer_id"
    const val QUANTITY_PROPERTY = "quantity"
    const val VARIANT_PROPERTY = "variant"
    const val PARENT_PRODUCT_NAME_PROPERTY = "parent product name"

    // Navigation
    const val TAB_SELECTED_EVENT = "tab selected"

    const val HOME_VALUE = "home"
    const val PRODUCTS_VALUE = "products"
    const val RECIPES_VALUE = "recipes"
    const val MY_ACCOUNT_VALUE = "my_account"
    const val BASKET_VALUE = "basket"

    // Products Section
    const val VIEWED_CATEGORY_EVENT = "viewed category"
    const val CHANGED_SECTION_EVENT = "changed section"
    const val PRODUCERS_VALUE = "producers"
    const val TAXON_VALUE = "taxon"
    const val SUBTAXON_VALUE = "subTaxon"
    const val VIEWED_SUBCATEGORY_EVENT = "viewed subcategory"

    // Order Confirmation
    const val VIEWED_ORDER_CONFIRMATION_EVENT = "viewed order confirmation"
    const val VIEWED_ORDER_DETAILS_EVENT = "viewed order details"
    const val INVITE_FRIENDS_TAPPED_EVENT = "invite friends tapped"

    const val UPCOMING_VALUE = "upcoming"
    const val WEEKLY_ORDER_VALUE = "weekly order"
    const val PAST_VALUE = "past"
    const val REVIEW_ORDER_POPUP_VALUE = "review order popup"
    const val ORDER_CONFIRMATION_VALUE = "order confirmation"

    // Search events
    const val TAPPED_SEARCH_EVENT = "tapped search"
    const val SEARCHED_PRODUCTS_EVENT = "searched products"
    const val QUERY = "query"

    // Producer details
    const val VIEWED_PRODUCER_DETAIL_EVENT = "viewed producer detail"
    const val SHOPFRONT_VALUE = "shopfront"
    const val PRODUCER_TAB_VALUE = "producer tab"
    const val HOMEPAGE_CAROUSEL_VALUE = "homepage carousel"

    // Products Tiles
    const val ADDED_PRODUCT_EVENT = "added product"
    const val REMOVED_PRODUCT_EVENT = "removed product"
    const val EVERY_WEEK_VALUE = "every week"
    const val THIS_WEEK_ONLY_VALUE = "this week only"
    const val PRE_ORDER_WEEK_ONLY_VALUE = "pre order week only"
    const val PRODUCER_PAGE_VALUE = "producer page"
    const val NORMAL_VALUE = "normal"
    const val INTERSTITIAL_VALUE = "interstitial"
    const val MOST_ORDERED_VALUE = "most ordered"
    const val PRODUCT_TILE_VALUE = "product tile"
    const val PRODUCT_DETAIL_VALUE = "product detail"
    const val PRODUCER_DETAIL_VALUE = "producer detail"
    const val PAST_ORDER_VALUE = "past order"
    const val PRODUCT_TILE_PRODUCER_PAGE_VALUE = "product tile - producer page"
    const val PRODUCT_TILE_PRODUCT_PAGE_VALUE = "product tile - product page"
    const val PRODUCT_TILE_ORDER_DETAILS_PAGE_VALUE = "product tile - past order page"
    const val NONE_VALUE = "none"

    // Date picker
    const val VIEWED_DATE_PICKER_EVENT = "viewed date picker"
    const val ORIGIN = "origin"
    const val ORIGIN_BASKET = "basket"
    const val ORIGIN_SHOP_FRONT = "shop front"
    const val ORIGIN_ORDER_SUMMARY = "order summary"
    const val AVAILABLE_SLOTS = "available slots"
    const val TAPPED_ON_DATE_PICKER_MAIN_SCREEN_EVENT = "tapped on date picker main screen"
    const val ACTION = "action"
    const val CANCELLED = "cancelled"
    const val SELECTED_SLOT = "selected slot"
}
