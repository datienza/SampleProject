package com.farmdrop.customer.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "id") val id: Int = 0,
    @Json(name = "name") val name: String? = null,
    @Json(name = "intro_paragraph") val introParagraph: String? = null,
    @Json(name = "images") val images: List<Image>? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "serves") val serves: String? = null,
    @Json(name = "shelf_life_days") val shelfLifeDays: String? = null,
    @Json(name = "origin") val origin: String? = null,
    @Json(name = "producer") val producer: Producer? = null,
    @Json(name = "properties") val properties: List<String>? = null,
    @Json(name = "master_taxon_id") val masterTaxonId: Int = 0,
    @Json(name = "sub_taxon_id") val subTaxonId: Int = 0,
    @Json(name = "producer_id") val producerId: Int = 0,
    @Json(name = "main_image_src") val mainImageSrc: String? = null,
    @Json(name = "position") val position: Int = 0,
    @Json(name = "taxon_ids") val taxonIds: List<Int>? = null,
    @Json(name = "marketing_tags") val marketingTags: List<MarketingTag>? = null,
    @Json(name = "ingredients") val ingredients: String? = null,
    @Json(name = "cooking_instructions") val cookingInstructions: String? = null,
    @Json(name = "storage_information") val storageInformation: String? = null,
    @Json(name = "booze") val isAlcohol: Boolean = false
)
