package com.farmdrop.customer

import android.app.Activity
import android.support.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.farmdrop.customer.data.wrapper.StripeWrapper
import com.farmdrop.customer.di.app.AppComponent
import com.farmdrop.customer.di.app.AppModule
import com.farmdrop.customer.di.app.DaggerAppComponent
import com.farmdrop.customer.utils.analytics.AnalyticsEvents
import com.farmdrop.customer.utils.cms.CMS
import io.fabric.sdk.android.Fabric
import io.realm.Realm
import timber.log.Timber
import uk.co.farmdrop.library.di.activity.ActivityComponentBuilder
import uk.co.farmdrop.library.di.activity.HasActivitySubcomponentBuilders
import uk.co.farmdrop.library.utils.analytics.AnalyticsHelper
import javax.inject.Inject
import javax.inject.Provider

class CustomerApplication : MultiDexApplication(), HasActivitySubcomponentBuilders {

    @Inject
    lateinit var mCustomerMessaging: CustomerMessaging

    @Inject
    lateinit var mActivityComponentBuilders: Map<Class<out Activity>, @JvmSuppressWildcards Provider<ActivityComponentBuilder<*, *>>>

    @Inject
    lateinit var mCMS: CMS

    @Inject
    lateinit var stripeWrapper: StripeWrapper

    lateinit var mAppComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        Fabric.with(this, Crashlytics())

        initAnalytics()
        mCustomerMessaging.initialise(this)
        Realm.init(this)
        stripeWrapper.initPaymentSession()
        mCMS.init()
    }

    private fun initAppComponent() {
        mAppComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(this))
                .build()
        mAppComponent.inject(this)
    }

    override fun getActivityComponentBuilder(activityClass: Class<out Activity>): ActivityComponentBuilder<*, *>? {
        return mActivityComponentBuilders[activityClass]?.get()
    }

    private fun initAnalytics() {
        AnalyticsHelper.init(applicationContext, BuildConfig.ANALYTICS_API_KEY)
        AnalyticsHelper.trackEvent(applicationContext, AnalyticsEvents.APPLICATION_STARTED, AnalyticsEvents.DATA_SOURCE_PROPERTY, AnalyticsEvents.DATA_SOURCE_VALUE)
    }
}
