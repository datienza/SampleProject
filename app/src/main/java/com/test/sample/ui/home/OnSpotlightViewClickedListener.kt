package com.farmdrop.customer.ui.home

interface OnSpotlightViewClickedListener {

    fun onProducerSpotlightClicked(producerId: Int)

    fun onProductSpotlightClicked(productId: Int)
}