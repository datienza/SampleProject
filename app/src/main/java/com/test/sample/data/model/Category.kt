package com.farmdrop.customer.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Category(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "name") val name: String? = null,
    @Json(name = "permalink") val permalink: String? = null,
    @Json(name = "parent_id") val parentId: Int = 0,
    @Json(name = "icon_src") val imageUrl: String? = null,
    @Json(name = "children") val children: List<SubCategory>? = null
)
