package com.farmdrop.customer.contracts.common

import uk.co.farmdrop.library.ui.base.MvpView

interface TimeLeftCountdownViewContract {
    interface View : MvpView {
        fun getRemainingTimeMinutesString(remainingMinutes: Int): String
        fun getRemainingTimeHoursString(remainingHours: Int): String
        fun getRemainingTimeHoursWithMinutesString(remainingHours: Int, remainingMinutes: Int): String
        fun getRemainingTimeDaysString(remainingDays: Int): String
        fun getRemainingTimeDaysWithHoursString(remainingDays: Int, remainingHours: Int): String
        fun displayTimeRemainingForOrderDetails(timeRemaining: String)
        fun displayNoTimeRemainingForOrderDetails()
        fun displayTimeRemainingForOrderTitle(timeRemaining: String)
        fun displayNoTimeRemainingForOrderTitle()
    }
}