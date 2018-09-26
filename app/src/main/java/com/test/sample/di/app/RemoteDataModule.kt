package com.farmdrop.customer.di.app

import com.farmdrop.customer.data.remote.CategoriesRemoteDataSource
import com.farmdrop.customer.data.remote.network.CategoriesApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteDataModule {

    @Provides
    @Singleton
    fun provideCategoriesRemoteDataSource(categoriesApi: CategoriesApi) =
        CategoriesRemoteDataSource(categoriesApi)
}
