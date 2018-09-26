package com.farmdrop.customer.di.app

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

const val NAME_ANDROID_SCHEDULER_MAIN_THREAD = "android_scheduler_main_thread"
const val NAME_IO_SCHEDULER = "io_scheduler"
const val NAME_COMPUTATION_SCHEDULER = "computation_scheduler"

@Module
class SchedulersModule {

    @Provides
    @Named(NAME_ANDROID_SCHEDULER_MAIN_THREAD)
    fun provideAndroidSchedulerMainThread(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Named(NAME_IO_SCHEDULER)
    fun provideIOScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Named(NAME_COMPUTATION_SCHEDULER)
    fun provideComputationScheduler(): Scheduler = Schedulers.computation()
}
