package com.farmdrop.customer.ui.products

import com.farmdrop.customer.data.model.CategoryData

interface OnCategorySelectedListener {
    fun onCategorySelected(selectedCategory: CategoryData)
}