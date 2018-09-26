package com.farmdrop.customer.di.app

import com.farmdrop.customer.CustomerApplication
import com.farmdrop.customer.di.activity.ActivityBindingModule
import dagger.Component
import javax.inject.Singleton

/**
 * Inject app dependencies.
 */

@Singleton
@Component(modules = [
    ActivityBindingModule::class,
    AppModule::class,
    NetModule::class,
    ManagerModule::class,
    RepositoryModule::class,
    LocalDataModule::class,
    RemoteDataModule::class,
    TimeModule::class,
    DataModule::class,
    SchedulersModule::class,
    ApiModule::class
])
interface AppComponent {
    fun inject(application: CustomerApplication): CustomerApplication
}
