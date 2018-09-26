package com.farmdrop.customer.di.app

import android.content.Context
import com.farmdrop.customer.utils.DateAndTimeFormatter
import dagger.Module
import dagger.Provides
import dagger.Reusable
import java.text.SimpleDateFormat
import javax.inject.Named

@Module
class DataModule {

    @Provides
    @Reusable
    fun provideDateAndTimeFormatter(
        context: Context,
        @Named(TimeModule.NAME_ISO_8601_DATE_FORMAT) inputDateFormat: SimpleDateFormat,
        @Named(TimeModule.NAME_DAY_OF_WEEK_DATE_FORMAT) dayOfWeekDateFormat: SimpleDateFormat,
        @Named(TimeModule.NAME_DAY_IN_MONTH_DATE_FORMAT) dayInMonthDateFormat: SimpleDateFormat,
        @Named(TimeModule.NAME_MONTH_DATE_FORMAT) monthDateFormat: SimpleDateFormat,
        @Named(TimeModule.NAME_INTERNATIONAL_HOUR_FORMAT) inputHourFormat: SimpleDateFormat,
        @Named(TimeModule.NAME_ENGLISH_HOUR_FORMAT) outputHourFormat: SimpleDateFormat
    ) =
            DateAndTimeFormatter(context, inputDateFormat, dayOfWeekDateFormat, dayInMonthDateFormat, monthDateFormat, inputHourFormat, outputHourFormat)
}
