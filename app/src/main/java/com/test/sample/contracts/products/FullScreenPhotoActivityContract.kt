package com.farmdrop.customer.contracts.products

import uk.co.farmdrop.library.ui.base.MvpView

interface FullScreenPhotoActivityContract {

    interface View : MvpView {
        fun displayPhoto(photoUrl: String)
        fun displayPlaceHolder()
    }
}