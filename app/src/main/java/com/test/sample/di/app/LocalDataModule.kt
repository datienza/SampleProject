package com.farmdrop.customer.di.app

import com.farmdrop.customer.data.local.ProductsLocalDataSource
import dagger.Module
import dagger.Provides
import io.realm.RealmConfiguration
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Provides
    @Singleton
    fun provideCategoriesLocalDataSource(realmConfiguration: RealmConfiguration) = CategoriesLocalDataSource(realmConfiguration)

    @Provides
    @Singleton
    fun provideProducersLocalDataSource(realmConfiguration: RealmConfiguration) = ProducersLocalDataSource(realmConfiguration)

    @Provides
    fun provideProductsLocalDataSource() = ProductsLocalDataSource()
}
