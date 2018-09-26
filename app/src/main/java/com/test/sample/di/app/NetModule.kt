package com.farmdrop.customer.di.app

import android.app.Application
import com.farmdrop.customer.utils.constants.NetworkConstants
import com.squareup.moshi.Moshi
import com.test.sample.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule {

    @Provides
    fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 20 * 1024 * 1024 // 20 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    fun provideLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun getOkHttpClient(cache: Cache, loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().cache(cache)
                .addNetworkInterceptor(loggingInterceptor)
                .readTimeout(NetworkConstants.NETWORK_READ_TIMEOUT, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    fun provideRetrofit(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit =
            getRetrofitBuilder(MoshiConverterFactory.create(moshi), okHttpClient).build()

    private fun getRetrofitBuilder(converterFactory: Converter.Factory, okHttpClient: OkHttpClient) =
            Retrofit.Builder()
                    .baseUrl(BuildConfig.API_ENDPOINT)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .addConverterFactory(converterFactory).addCallAdapterFactory(
                            RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
}
