package com.farmdrop.customer.data.remote

import com.farmdrop.customer.data.model.Category
import com.farmdrop.customer.data.remote.network.CategoriesApi
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import uk.co.farmdrop.library.data.model.wrappers.ListResponseWrapper

class CategoriesRemoteDataSource(private val retrofitApi: CategoriesApi) {

    fun get(): Single<ListResponseWrapper<Category>> =
        retrofitApi.getTaxonomies()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
}