package com.farmdrop.customer.presenters.products

import com.farmdrop.customer.contracts.products.FullScreenPhotoActivityContract
import uk.co.farmdrop.library.ui.base.BasePresenter

class FullScreenPhotoActivityPresenter :
        BasePresenter<FullScreenPhotoActivityContract.View>() {

    fun displayPhoto(photoUrl: String?) {
        if (photoUrl != null) {
            mvpView.displayPhoto(photoUrl)
        } else {
            mvpView.displayPlaceHolder()
        }
    }
}