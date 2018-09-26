package com.farmdrop.customer.ui.adapter.ordersummary

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farmdrop.customer.R
import com.farmdrop.customer.presenters.ordersummary.FindAddressFragmentPresenter
import com.farmdrop.customer.ui.ordersummary.AddressItemView
import uk.co.farmdrop.library.ui.base.BaseAdapter

class AddressesAdapter(context: Context) : BaseAdapter<AddressData, AddressesAdapter.AddressViewHolder>() {

    private var mPresenter: FindAddressFragmentPresenter? = null

    private val mInflater = LayoutInflater.from(context)

    fun setFindAddressFragmentPresenter(presenter: FindAddressFragmentPresenter) {
        mPresenter = presenter
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val addressData = mData[position]
        holder.bind(addressData)
        holder.itemView.setOnClickListener {
            mPresenter?.onAddressSelected(addressData, position)
            addressData.selected = true
            notifyItemChanged(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = mInflater.inflate(R.layout.item_address, parent, false)
        return AddressesAdapter.AddressViewHolder(view)
    }

    class AddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(addressData: AddressData) {
            (itemView as AddressItemView).bind(addressData)
        }
    }
}
