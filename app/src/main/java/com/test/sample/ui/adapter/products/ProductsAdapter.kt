package com.farmdrop.customer.ui.adapter.products

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farmdrop.customer.R
import com.farmdrop.customer.ui.products.CarouselProductView
import com.farmdrop.customer.ui.products.DefaultProductView
import com.farmdrop.customer.ui.products.OnProductSelectedListener
import com.farmdrop.customer.utils.analytics.ProductViewLocation
import uk.co.farmdrop.library.ui.base.BaseAdapter

class ProductsAdapter(private val productViewLocation: ProductViewLocation?) : BaseAdapter<ProductData, ProductsAdapter.ProductViewHolder>() {

    var onProductSelectedListener: OnProductSelectedListener? = null

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productData = mData[position]
        holder.bind(productData)
        holder.itemView.setOnClickListener {
            onProductSelectedListener?.onProductSelected(productData)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemLayout = if (productViewLocation != null && productViewLocation.location != ProductViewLocation.PRODUCT_SUBTAXON) {
            R.layout.item_carousel_product
        } else {
            R.layout.item_product
        }
        val view = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
        return ProductsAdapter.ProductViewHolder(view, productViewLocation)
    }

    class ProductViewHolder(itemView: View, private val productViewLocation: ProductViewLocation?) : RecyclerView.ViewHolder(itemView) {
        fun bind(productData: ProductData) = when (itemView) {
            is CarouselProductView ->
                itemView.bind(productData, productViewLocation, adapterPosition)
            else -> {
                val productView = itemView as DefaultProductView
                productView.bind(productData, productViewLocation, adapterPosition)
            }
        }
    }
}
