package com.farmdrop.customer.ui.adapter.delivery

import android.view.View
import com.farmdrop.customer.data.model.delivery.DeliverySlotData

interface DeliverySlotActionListener {
    fun onDeliverySlotSelected(view: View, deliverySlot: DeliverySlotData, actionFromUser: Boolean)
}