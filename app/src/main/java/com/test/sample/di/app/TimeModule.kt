package com.farmdrop.customer.di.app

import dagger.Module
import dagger.Provides
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Named

@Module
class TimeModule {

    companion object {
        const val NAME_ISO_8601_DATE_FORMAT = "iso_8601_date_format"
        const val ISO_8601_DATE_FORMAT = "yyyy-MM-dd"

        const val NAME_DAY_OF_WEEK_DATE_FORMAT = "name_day_of_week_date_format"
        const val DAY_OF_WEEK_DATE_FORMAT = "EEE"

        const val NAME_DAY_IN_MONTH_DATE_FORMAT = "name_day_of_month_date_format"
        const val DAY_IN_MONTH_DATE_FORMAT = "d"

        const val NAME_MONTH_DATE_FORMAT = "name_month_date_format"
        const val MONTH_DATE_FORMAT = "MMM"

        const val NAME_INTERNATIONAL_HOUR_FORMAT = "name_international_hour_format"
        const val INTERNATIONAL_HOUR_FORMAT = "HH:mm" // the real format

        const val NAME_ENGLISH_HOUR_FORMAT = "name_english_hour_format"
        const val ENGLISH_HOUR_FORMAT = "h:mmaa" // format imposed by a king
    }

    @Provides
    @Named(NAME_ISO_8601_DATE_FORMAT)
    fun provideIso8601DateFormat(locale: Locale): SimpleDateFormat = SimpleDateFormat(ISO_8601_DATE_FORMAT, locale)

    @Provides
    @Named(NAME_DAY_OF_WEEK_DATE_FORMAT)
    fun provideDayOfWeekFormat(locale: Locale): SimpleDateFormat = SimpleDateFormat(DAY_OF_WEEK_DATE_FORMAT, locale)

    @Provides
    @Named(NAME_DAY_IN_MONTH_DATE_FORMAT)
    fun provideDayInMonthDateFormat(locale: Locale): SimpleDateFormat = SimpleDateFormat(DAY_IN_MONTH_DATE_FORMAT, locale)

    @Provides
    @Named(NAME_MONTH_DATE_FORMAT)
    fun provideMonthDateFormat(locale: Locale): SimpleDateFormat = SimpleDateFormat(MONTH_DATE_FORMAT, locale)

    @Provides
    @Named(NAME_INTERNATIONAL_HOUR_FORMAT)
    fun provideInternationalHourFormat(locale: Locale): SimpleDateFormat = SimpleDateFormat(INTERNATIONAL_HOUR_FORMAT, locale)

    @Provides
    @Named(NAME_ENGLISH_HOUR_FORMAT)
    fun provideEnglishHourFormat(locale: Locale): SimpleDateFormat = SimpleDateFormat(ENGLISH_HOUR_FORMAT, locale)

    @Provides
    fun provideLocale(): Locale = Locale.getDefault()
}