package com.farmdrop.customer.extensions

import android.support.annotation.DrawableRes
import android.support.v7.widget.SearchView
import android.widget.ImageView
import android.widget.LinearLayout
import com.farmdrop.customer.R

/**
 * Provide method extensions for SearchView class.
 */
fun SearchView.setIcon(@DrawableRes icon: Int) {
    val searchViewIcon = findViewById<ImageView>(R.id.search_mag_icon)
    searchViewIcon.setImageResource(icon)
}

fun SearchView.setTextStyle(style: Int) {
    val autoCompleteTextView = findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
    autoCompleteTextView.setTextStyle(style)
}

fun SearchView.setTextMargin(start: Int, top: Int, end: Int, bottom: Int) {
    val autoCompleteTextView = findViewById<SearchView.SearchAutoComplete>(R.id.search_src_text)
    val layoutParams = autoCompleteTextView.layoutParams as LinearLayout.LayoutParams
    layoutParams.marginStart = start
    layoutParams.marginEnd = end
    layoutParams.topMargin = top
    layoutParams.bottomMargin = bottom
}
