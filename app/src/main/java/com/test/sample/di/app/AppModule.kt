package com.farmdrop.customer.di.app

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.farmdrop.customer.utils.constants.Constants
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.realm.Realm
import io.realm.RealmConfiguration
import java.text.DecimalFormat
import java.util.Random
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    companion object {
        const val NAME_PRICE_DECIMAL_FORMAT = "name_price_decimal_format"
        const val NAME_UNIT_DECIMAL_FORMAT = "name_unit_decimal_format"
    }

    @Provides
    @Singleton
    fun getApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides
    fun getResources(): Resources {
        return application.resources
    }

    @Provides
    @Singleton
    fun provideRealmConfiguration(): RealmConfiguration {
        @Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
        return RealmConfiguration.Builder()
                .schemaVersion(Constants.REALM_DATABASE_SCHEMA_VERSION.toLong())
                .deleteRealmIfMigrationNeeded()
                .modules(Realm.getDefaultModule())
                .build()
    }

    @Provides
    @Singleton
    fun provideRealm(realmConfiguration: RealmConfiguration): Realm {
        return Realm.getInstance(realmConfiguration)
    }

    @Provides
    @Named(NAME_PRICE_DECIMAL_FORMAT)
    fun providePriceDecimalFormat(): DecimalFormat {
        return DecimalFormat("0.00")
    }

    @Provides
    @Named(NAME_UNIT_DECIMAL_FORMAT)
    fun provideUnitDecimalFormat(): DecimalFormat {
        return DecimalFormat("#.##")
    }

    @Provides
    @Reusable
    fun provideRandom() = Random()
}
