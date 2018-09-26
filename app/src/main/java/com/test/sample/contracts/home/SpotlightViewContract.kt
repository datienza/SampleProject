package com.farmdrop.customer.contracts.home

import uk.co.farmdrop.library.ui.base.MvpView

interface SpotlightViewContract {
    interface View : MvpView {
        fun showPillText()

        fun setPillText(pillText: String)

        fun setTitleText(titleText: String)

        fun setDescriptionText(descriptionText: String)

        fun setImage(imageUrl: String)

        fun hideView()

        fun setListenerForProduct(productId: Int)

        fun setListenerForProducer(producerId: Int)
    }
}