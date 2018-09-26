package com.farmdrop.customer.data.remote.network

import com.farmdrop.customer.data.model.Category
import io.reactivex.Single
import retrofit2.http.GET
import uk.co.farmdrop.library.data.model.wrappers.ListResponseWrapper

interface CategoriesApi {

    @GET("/1/taxons/shop_taxon_tree")
    fun getTaxonomies(): Single<ListResponseWrapper<Category>>
}