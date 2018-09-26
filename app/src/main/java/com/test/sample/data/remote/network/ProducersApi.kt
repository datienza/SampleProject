package com.farmdrop.customer.data.remote.network

import com.farmdrop.customer.data.wrapper.PaginatedListResponseWrapper
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import uk.co.farmdrop.library.data.model.wrappers.ResponseWrapper

interface ProducersApi {

    /**
     * this call can accept the following queries:
     * {@link com.farmdrop.customer.utils.constants.NetworkQueryKeys#DELIVERY_AREA_QUERY_KEY}
     * {@link com.farmdrop.customer.utils.constants.NetworkQueryKeys#PAGE_NUMBER_QUERY_KEY}
     * {@link com.farmdrop.customer.utils.constants.NetworkQueryKeys#PER_PAGE_LIMIT_QUERY_KEY}
     */

    @GET("/2/producers")
    fun getProducers(@QueryMap queryMap: Map<String, String>): Single<PaginatedListResponseWrapper<Producer>>

    @GET("2/producers/{id}")
    fun getProducer(@Path("id") id: Int): Single<ResponseWrapper<Producer>>
}
