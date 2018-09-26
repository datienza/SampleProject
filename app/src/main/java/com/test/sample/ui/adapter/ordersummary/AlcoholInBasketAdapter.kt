package com.farmdrop.customer.ui.adapter.ordersummary

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farmdrop.customer.R
import com.farmdrop.customer.data.model.order.BasketItemData
import com.farmdrop.customer.ui.basket.BasketItemView
import uk.co.farmdrop.library.ui.base.BaseAdapter

class AlcoholInBasketAdapter(context: Context) : BaseAdapter<BasketItemData, AlcoholInBasketAdapter.AlcoholInBasketViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlcoholInBasketViewHolder {
        val view = inflater.inflate(R.layout.item_basket, parent, false)
        return AlcoholInBasketAdapter.AlcoholInBasketViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlcoholInBasketViewHolder, position: Int) =
            holder.bind(mData[position])

    class AlcoholInBasketViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(basketItemData: BasketItemData) {
            val basketItemView = itemView as BasketItemView
            basketItemView.bind(basketItemData, true)
        }
    }
}