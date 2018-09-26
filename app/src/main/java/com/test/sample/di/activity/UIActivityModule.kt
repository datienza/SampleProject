package com.farmdrop.customer.di.activity

import android.support.v7.app.AppCompatActivity
import com.farmdrop.customer.utils.exceptions.LogInResolution
import com.farmdrop.customer.utils.exceptions.UIResolution
import com.farmdrop.customer.utils.exceptions.UIResolver
import dagger.Module
import dagger.Provides
import uk.co.farmdrop.library.di.activity.ActivityScope

@Module
class UIActivityModule {

    @Provides
    @ActivityScope
    internal fun providesUIResolver(activity: AppCompatActivity) = UIResolver(activity)

    @Provides
    @ActivityScope
    internal fun providesUIResolution(uiResolver: UIResolver) = UIResolution(uiResolver)

    @Provides
    @ActivityScope
    internal fun providesLoginResolution(uiResolver: UIResolver) = LogInResolution(uiResolver)
}
