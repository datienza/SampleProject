package com.farmdrop.customer.ui.adapter.delivery

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farmdrop.customer.R
import com.farmdrop.customer.data.model.delivery.DeliverySlotData
import com.farmdrop.customer.ui.delivery.DatePickerTimeSlotView

class DeliverySlotsAdapter(context: Context, private val mDeliverySlots: List<DeliverySlotData>, private val mDeliverySlotActionListener: DeliverySlotActionListener?) :
        RecyclerView.Adapter<DeliverySlotsAdapter.ViewHolder>() {

    private val mInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_date_picker_delivery_slot, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = mDeliverySlots.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDeliverySlots[position]
        holder.bind(item, mDeliverySlotActionListener)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: DeliverySlotData, deliverySlotActionListener: DeliverySlotActionListener?) {
            (itemView as DatePickerTimeSlotView).bind(item, deliverySlotActionListener)
        }
    }
}
