package com.farmdrop.customer.ui.adapter.products

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.farmdrop.customer.R
import com.farmdrop.customer.data.model.CategoryData
import com.farmdrop.customer.ui.products.OnCategorySelectedListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_category.categoryImage
import kotlinx.android.synthetic.main.item_category.categoryName
import uk.co.farmdrop.library.ui.base.BaseAdapter
import uk.co.farmdrop.library.utils.image.ImageManager
import uk.co.farmdrop.library.utils.image.ImageUtils

class CategoriesAdapter(context: Context) : BaseAdapter<CategoryData, CategoriesAdapter.CategoryViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    var onCategorySelectedListener: OnCategorySelectedListener? = null

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryData = mData[position]
        holder.bind(categoryData)
        holder.itemView.setOnClickListener {
            onCategorySelectedListener?.onCategorySelected(categoryData)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = layoutInflater.inflate(R.layout.item_category, parent, false)
        return CategoriesAdapter.CategoryViewHolder(view)
    }

    class CategoryViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(categoryData: CategoryData) {
            ImageManager.loadImageFromDimenResSize(itemView.context, categoryData.imageUrl.toString(), categoryImage, R.drawable.ic_placeholder_square_image_selfie_carrot,
                    R.dimen.category_image_size, R.dimen.category_image_size, ImageUtils.RoundImageTransform())

            categoryName.text = categoryData.name
        }
    }
}
