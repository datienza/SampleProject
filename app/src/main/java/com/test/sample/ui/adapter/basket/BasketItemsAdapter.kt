package com.farmdrop.customer.ui.adapter.basket

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farmdrop.customer.R
import com.farmdrop.customer.data.model.order.BasketItemContent
import com.farmdrop.customer.data.model.order.BasketItemData
import com.farmdrop.customer.data.model.order.TYPE_BASKET_ITEM_HEADER
import com.farmdrop.customer.ui.basket.BasketItemView
import com.farmdrop.customer.ui.basket.OnBasketItemSelectedListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.header_current_delivery_slot.currentDeliverySlotView
import kotlinx.android.synthetic.main.item_basket_header.itemsInBasketTextView
import uk.co.farmdrop.library.ui.base.BaseAdapter
import uk.co.farmdrop.library.utils.Constants.ZERO

class BasketItemsAdapter : BaseAdapter<BasketItemContent, RecyclerView.ViewHolder>() {

    var onBasketItemSelectedListener: OnBasketItemSelectedListener? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BasketHeaderViewHolder) {
            val basketHeader = mData[position] as BasketItemContent.Header
            holder.bind(basketHeader, onBasketItemSelectedListener)
        }
        if (holder is BasketItemViewHolder) {
            val basketItemData = mData[position] as BasketItemContent.BasketItem
            holder.bind(basketItemData.data, onBasketItemSelectedListener)
        }
    }

    override fun getItemViewType(position: Int) =
        mData[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (viewType) {
            TYPE_BASKET_ITEM_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_basket_header, parent, false)
                BasketHeaderViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_basket, parent, false)
                BasketItemViewHolder(view)
            }
        }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        cleanViewHolder(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        cleanViewHolder(holder)
    }

    private fun cleanViewHolder(holder: RecyclerView.ViewHolder) {
        if (holder is BasketHeaderViewHolder) {
            holder.destroyDisposables()
        }
    }

    class BasketHeaderViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(basketItemContent: BasketItemContent.Header, onBasketItemSelectedListener: OnBasketItemSelectedListener?) {
            currentDeliverySlotView.setChevronImageUp(false)
            currentDeliverySlotView.setOnClickListener {
                onBasketItemSelectedListener?.onDatePickerClicked()
            }
            currentDeliverySlotView.displayCurrentSlot()

            if (basketItemContent.basketItemsCount == ZERO) {
                itemsInBasketTextView.visibility = View.GONE
            } else {
                itemsInBasketTextView.visibility = View.VISIBLE
                itemsInBasketTextView.text = itemView.resources.getQuantityString(R.plurals.items_in_your_basket, basketItemContent.basketItemsCount, basketItemContent.basketItemsCount)
            }
        }

        fun destroyDisposables() {
            currentDeliverySlotView.destroyAllDisposables()
        }
    }

    class BasketItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(basketItemData: BasketItemData, onBasketItemSelectedListener: OnBasketItemSelectedListener?) {
            val basketItemView = itemView as BasketItemView
            basketItemView.bind(basketItemData, false)

            itemView.setOnClickListener {
                onBasketItemSelectedListener?.onBasketLineItemSelected(basketItemData)
            }
        }
    }
}
