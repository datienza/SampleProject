package com.farmdrop.customer.ui.adapter.delivery

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farmdrop.customer.R
import com.farmdrop.customer.data.model.delivery.DeliverySlotData
import com.farmdrop.customer.ui.delivery.DatePickerTimeSlotsListsForDurationView
import com.farmdrop.customer.utils.MutablePair

class DatePickerTimeSlotsListsForDurationViewAdapter(context: Context) : RecyclerView.Adapter<DatePickerTimeSlotsListsForDurationViewAdapter.ViewHolder>() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    var mDeliverySlots: List<MutablePair<Float, List<MutablePair<String, List<DeliverySlotData>>>>>? = null

    var mDeliverySlotActionListener: DeliverySlotActionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item_date_picker_list_delivery_slots, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mDeliverySlots?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mDeliverySlots?.get(position)
        if (item != null) {
            holder.bind(item, mDeliverySlotActionListener)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(item: MutablePair<Float, List<MutablePair<String, List<DeliverySlotData>>>>, deliverySlotActionListener: DeliverySlotActionListener?) {
            (itemView as DatePickerTimeSlotsListsForDurationView).bind(item, deliverySlotActionListener)
        }
    }
}
