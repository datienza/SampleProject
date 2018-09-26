package com.farmdrop.customer.contracts.producers

import uk.co.farmdrop.library.ui.base.MvpView

/**
 * Define contract methods between ProducerDetailsActivity & ProducerDetailsActivityPresenter
 */
interface ProducerDetailsActivityContract {

    interface View : MvpView {
        fun displayData(name: String?, description: String?, images: List<ImageData>?)
        fun showShortDescription(shortDescription: String?)
        fun showLongDescription(longDescription: String?)
        fun hideReadMore()
        fun showEmptyDetailsText()
        fun hideDetailsText()
    }
}
