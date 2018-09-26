package com.farmdrop.customer.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pagination(
    @Json(name = "current") val current: Int = 0,
    @Json(name = "previous") val previous: Int? = 0,
    @Json(name = "next") val next: Int? = 0,
    @Json(name = "per_page") val perPage: Int = 0,
    @Json(name = "pages") val pages: Int = 0,
    @Json(name = "count") val count: Int = 0
)