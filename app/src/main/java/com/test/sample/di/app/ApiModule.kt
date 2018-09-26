package com.farmdrop.customer.di.app

import com.farmdrop.customer.data.remote.network.CategoriesApi
import com.farmdrop.customer.data.remote.network.ProducersApi
import com.farmdrop.customer.utils.constants.DaggerNetConstants.DELIVERY_AREA_QUERY_RETROFIT
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

@Module
class ApiModule {

    @Provides
    fun provideCategoriesApi(@Named(DELIVERY_AREA_QUERY_RETROFIT) retrofit: Retrofit): CategoriesApi = retrofit.create(CategoriesApi::class.java)

    @Provides
    fun provideProducersApi(@Named(DELIVERY_AREA_QUERY_RETROFIT) retrofit: Retrofit): ProducersApi = retrofit.create(ProducersApi::class.java)
}
