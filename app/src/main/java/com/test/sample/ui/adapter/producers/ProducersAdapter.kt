package com.farmdrop.customer.ui.adapter.producers

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farmdrop.customer.R
import com.farmdrop.customer.data.model.ProducerData
import com.farmdrop.customer.ui.common.ProducerCardView
import com.farmdrop.customer.ui.producers.OnProducerSelectedListener
import kotlinx.android.extensions.LayoutContainer
import uk.co.farmdrop.library.ui.base.BaseAdapter

class ProducersAdapter : BaseAdapter<ProducerData, ProducersAdapter.ProducersViewHolder>() {

    var mOnProducerSelectedListener: OnProducerSelectedListener? = null

    override fun onBindViewHolder(holder: ProducersViewHolder, position: Int) {
        val producerData = mData[position]
        holder.bind(producerData)
        holder.itemView.setOnClickListener {
            mOnProducerSelectedListener?.onProducerSelected(producerData)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProducersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.producer_card_view, parent, false)
        return ProducersAdapter.ProducersViewHolder(view)
    }

    class ProducersViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(producerData: ProducerData) {
            (itemView as ProducerCardView).bind(producerData)
        }
    }
}
