package com.farmdrop.customer.ui.adapter.account

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farmdrop.customer.R
import com.farmdrop.customer.utils.cms.EthicalReminder
import kotlinx.android.extensions.LayoutContainer
import uk.co.farmdrop.library.ui.base.BaseAdapter

class EthicalRemindersAdapter : BaseAdapter<EthicalReminder, EthicalRemindersAdapter.EthicalRemindersViewHolder>() {

    override fun onBindViewHolder(holder: EthicalRemindersViewHolder, position: Int) {
        val producerData = mData[position]
        holder.bind(producerData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EthicalRemindersViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.account_ethical_reminder_view, parent, false)
        return EthicalRemindersAdapter.EthicalRemindersViewHolder(view)
    }

    class EthicalRemindersViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(ethicalReminder: EthicalReminder) {
            (itemView as EthicalReminderView).bindEthicalInfo(ethicalReminder)
        }
    }
}
