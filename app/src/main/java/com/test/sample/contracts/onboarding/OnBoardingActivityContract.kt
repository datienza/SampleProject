package com.farmdrop.customer.contracts.onboarding

import com.farmdrop.customer.ui.onboarding.OnBoardingPagerAdapter
import uk.co.farmdrop.library.ui.base.MvpView

interface OnBoardingActivityContract {
    interface View : MvpView {
        fun setLocalAdapterInfo()
        fun getPagesBackgroundImgResources(): IntArray
        fun setRemoteAdapterInfo(pagesInfoList: ArrayList<OnBoardingPagerAdapter.PageInfo>)
        fun getScrollStageSettlingConst(): Int
        fun getScrollStageIdleConst(): Int
        fun launchPostcodeChecker()
    }
}
