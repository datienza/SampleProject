package com.farmdrop.customer.ui.adapter.delivery

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.farmdrop.customer.R
import com.farmdrop.customer.utils.DateAndTimeFormatter

// dayOfWeekDateFormat, dayInMonthDateFormat, monthDateFormat
class DaysTopHeaderAdapter(context: Context, private val mDateAndTimeFormatter: DateAndTimeFormatter) :
        RecyclerView.Adapter<DaysTopHeaderAdapter.ViewHolder>() {

    private val mLayoutInflater = LayoutInflater.from(context)

    var mDays: List<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mLayoutInflater.inflate(R.layout.item_date_picker_day_top_header, parent, false)
        return ViewHolder(view, mDateAndTimeFormatter)
    }

    override fun getItemCount(): Int {
        return mDays?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = mDays?.get(position)
        if (day != null) {
            holder.bind(day)
        }
    }

    class ViewHolder(view: View, private val mDateAndTimeFormatter: DateAndTimeFormatter) :
            RecyclerView.ViewHolder(view) {

        fun bind(day: String) {
            (itemView as TextView).text = mDateAndTimeFormatter.formatDeliverySlotDayForDisplay(day, R.string.date_picker_date_header_format)
        }
    }
}
