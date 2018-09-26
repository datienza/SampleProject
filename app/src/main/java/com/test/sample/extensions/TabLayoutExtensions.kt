package com.farmdrop.customer.extensions

import android.support.design.widget.TabLayout
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

fun TabLayout.setTabTextStyle(tabPosition: Int, style: Int) {
    val tabLayout = (getChildAt(0) as ViewGroup).getChildAt(tabPosition) as LinearLayout
    val tabTextView = tabLayout.getChildAt(1) as TextView
    tabTextView.setTextStyle(style)
}