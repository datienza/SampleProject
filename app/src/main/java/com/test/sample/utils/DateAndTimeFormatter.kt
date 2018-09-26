package com.farmdrop.customer.utils

import android.content.Context
import java.text.SimpleDateFormat

class DateAndTimeFormatter(
    private val context: Context,
    private val inputDaysFormat: SimpleDateFormat,
    private val dayOfWeekDateFormat: SimpleDateFormat,
    private val dayInMonthDateFormat: SimpleDateFormat,
    private val monthDateFormat: SimpleDateFormat,
    private val inputHourFormat: SimpleDateFormat,
    private val outputHourFormat: SimpleDateFormat
) {

    companion object {
        const val ISO_8601_HOUR_AND_MIN_START_STRING_INDEX = 11
        const val ISO_8601_HOUR_AND_MIN_END_STRING_INDEX = 16
        const val ISO_8601_YYYY_MM_DD_START_INDEX = 0
        const val ISO_8601_YYYY_MM_DD_END_INDEX = 10

        const val ISO_8601_DATE_HOURS_MINUTES_INSERT_FORMAT = "%sT%s:00.000+00:00"
        const val TIMES_CONCAT_FORMAT = "%s-%s"
        const val TIMES_CONCAT_FIRST_START_INDEX = 0
        const val TIMES_CONCAT_FIRST_END_INDEX = 5
        const val TIMES_CONCAT_SECOND_START_INDEX = 6
        const val TIMES_CONCAT_SECOND_END_INDEX = 11
    }

    fun format24To12HoursTime(time: String): String {
        val timeDate = inputHourFormat.parse(time)
        return outputHourFormat.format(timeDate).toLowerCase()
    }

    fun extractTimeFromIso8601(dateAndTime: String?) =
            dateAndTime?.substring(ISO_8601_HOUR_AND_MIN_START_STRING_INDEX, ISO_8601_HOUR_AND_MIN_END_STRING_INDEX)

    fun extractDateFromIso8601(dateAndTime: String?) =
            dateAndTime?.substring(ISO_8601_YYYY_MM_DD_START_INDEX, ISO_8601_YYYY_MM_DD_END_INDEX)

    fun createIso8601FromDateAndTime(dayFormatted: String, hoursAndMinutesFormatted: String) =
            String.format(ISO_8601_DATE_HOURS_MINUTES_INSERT_FORMAT, dayFormatted, hoursAndMinutesFormatted)

    fun concatTimes(time1: String?, time2: String?) = String.format(TIMES_CONCAT_FORMAT, time1, time2)

    fun extractFirstTimeFromConcat(times: String) = times.substring(TIMES_CONCAT_FIRST_START_INDEX, TIMES_CONCAT_FIRST_END_INDEX)

    fun extractSecondTimeFromConcat(times: String) = times.substring(TIMES_CONCAT_SECOND_START_INDEX, TIMES_CONCAT_SECOND_END_INDEX)
}
