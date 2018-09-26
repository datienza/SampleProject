package com.farmdrop.customer.di.app

import com.farmdrop.customer.data.remote.CategoriesRemoteDataSource
import com.farmdrop.customer.data.repository.CategoriesRepository
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.realm.Realm
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideCategoriesRepository(
        categoriesLocalDataSource: CategoriesLocalDataSource,
        categoriesRemoteDataSource: CategoriesRemoteDataSource,
        realm: Realm,
        connectionChecker: ConnectionChecker,
        @Named(NAME_ANDROID_SCHEDULER_MAIN_THREAD) scheduler: Scheduler
    ) = CategoriesRepository(categoriesLocalDataSource, categoriesRemoteDataSource, realm, connectionChecker, scheduler)

    @Provides
    @Singleton
    fun provideProducersRepository(
        producersLocalDataSource: ProducersLocalDataSource,
        producersRemoteDataSource: ProducersRemoteDataSource,
        realm: Realm,
        connectionChecker: ConnectionChecker,
        @Named(NAME_ANDROID_SCHEDULER_MAIN_THREAD) scheduler: Scheduler
    ) = ProducersRepository(producersLocalDataSource, producersRemoteDataSource, realm, connectionChecker, scheduler)
}
