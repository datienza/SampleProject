package com.farmdrop.customer.contracts.common

import android.support.annotation.DrawableRes
import uk.co.farmdrop.library.ui.base.MvpView

interface ProducerCardViewContract {
    interface View : MvpView {
        fun loadImage(imagePath: String)
        fun loadPlaceHolderImage(@DrawableRes placeHolderRes: Int)
        fun setName(name: String)
        fun setDescription(description: String)
        fun showProducerOfTheWeekTag()
        fun hideProducerOfTheWeekTag()
    }
}