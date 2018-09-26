package com.farmdrop.customer.contracts.splash

import uk.co.farmdrop.library.ui.base.MvpView

interface SplashActivityContract {

    interface View : MvpView {

        fun displayOnBoardingActivity()

        fun displayPostcodeCheckerActivity()

        fun finishSplash()
    }
}
