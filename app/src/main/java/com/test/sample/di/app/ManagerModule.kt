package com.farmdrop.customer.di.app

import android.content.Context
import com.farmdrop.customer.managers.FontsManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ManagerModule {
    @Provides
    @Singleton
    fun provideFontsManager(context: Context) = FontsManager(context)
}
