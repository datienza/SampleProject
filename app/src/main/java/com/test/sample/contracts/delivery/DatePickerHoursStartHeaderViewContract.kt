package com.farmdrop.customer.contracts.delivery

import com.farmdrop.customer.data.model.delivery.OpeningTimes
import uk.co.farmdrop.library.ui.base.MvpView

interface DatePickerHoursStartHeaderViewContract {
    interface View : MvpView {
        fun displayTimes(openingTimes: OpeningTimes)
        fun getFirstTimesOfDurationTopPadding(): Int
        fun getLastTimesOfDurationWithEtaMessageBottomPadding(): Int
        fun setVerticalPadding(newPaddingTop: Int, newPaddingBottom: Int)
    }
}