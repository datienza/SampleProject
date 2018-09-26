package com.farmdrop.customer.contracts.ordersummary

import uk.co.farmdrop.library.ui.base.MvpView

interface DriverInstructionsFragmentContract {
    interface View : MvpView {
        fun setDriverInstructions(driverInstructions: String)
        fun closeFragmentWithNewSpecialInstructions(driverInstructions: String)
        fun setDriverInstructionsTextRed()
        fun resetDriverInstructionsToDefaultColor()
    }
}