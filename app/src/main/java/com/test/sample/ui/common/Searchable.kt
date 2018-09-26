package com.farmdrop.customer.ui.common

interface Searchable {
    fun performSearch(query: String?, querySubmitted: Boolean)
    fun clearSearch()
}