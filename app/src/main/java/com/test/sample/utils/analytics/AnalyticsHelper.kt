package com.farmdrop.customer.utils.analytics

import android.content.Context
import com.segment.analytics.Properties
import uk.co.farmdrop.library.utils.analytics.AnalyticsHelper

object AnalyticsHelper : AnalyticsHelper() {

    fun trackEventWithDataSource(context: Context, event: String) {
        trackEvent(context, event, AnalyticsEvents.DATA_SOURCE_PROPERTY, AnalyticsEvents.DATA_SOURCE_VALUE)
    }

    fun trackEventWithDataSource(context: Context, event: String, vararg keyValues: String) {
        trackEvent(context, event, AnalyticsEvents.DATA_SOURCE_PROPERTY, AnalyticsEvents.DATA_SOURCE_VALUE, *keyValues)
    }

    fun trackEventWithDataSource(context: Context, event: String, properties: Properties) {
        properties[AnalyticsEvents.DATA_SOURCE_PROPERTY] = AnalyticsEvents.DATA_SOURCE_VALUE
        trackEvent(context, event, properties)
    }
}
