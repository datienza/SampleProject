package com.farmdrop.customer.contracts.common

import uk.co.farmdrop.library.ui.base.MvpView

interface DriverInstructionsDetailsViewContract {
    interface View : MvpView {
        fun showDriverInstructionsPreviewText(driverInstructions: String)
        fun showEnterDriverInstructionsPreviewText()
        fun hideChevron()
    }
}
