package com.farmdrop.customer.ui.adapter.delivery

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farmdrop.customer.R
import com.farmdrop.customer.data.model.delivery.OpeningTimes
import com.farmdrop.customer.ui.delivery.DatePickerHoursStartHeaderView

class OpeningTimesStartHeaderAdapter(context: Context) :
        RecyclerView.Adapter<OpeningTimesStartHeaderAdapter.ViewHolder>() {

    private val mLayoutInflater = LayoutInflater.from(context)

    var mOpeningTimesList: List<OpeningTimes>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mLayoutInflater.inflate(R.layout.item_date_picker_hours_start_header, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mOpeningTimesList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val openingTimes = mOpeningTimesList?.get(position)
        if (openingTimes != null) {
            holder.bind(mOpeningTimesList, position)
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(openingTimesList: List<OpeningTimes>?, position: Int) {
            (itemView as DatePickerHoursStartHeaderView).bind(openingTimesList, position)
        }
    }
}
