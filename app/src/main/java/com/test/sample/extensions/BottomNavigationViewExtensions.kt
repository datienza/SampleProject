package com.farmdrop.customer.extensions

import android.annotation.SuppressLint
import android.support.design.bottomnavigation.LabelVisibilityMode
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.view.View
import android.widget.TextView
import com.farmdrop.customer.R

/**
 * Provide method extensions for BottomNavigationView class.
 */

@SuppressLint("RestrictedApi")
fun BottomNavigationView.disableShiftMode() {
    val menuView = this.getChildAt(0) as BottomNavigationMenuView
    menuView.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
    for (i in 0 until menuView.childCount) {
        val item = menuView.getChildAt(i) as BottomNavigationItemView
        item.setShifting(false)
        // set once again checked value, so view will be updated
        item.setChecked(item.itemData.isChecked)
    }
}

fun BottomNavigationView.setSelectedViewTextStyle(menuItemId: Int, style: Int) {
    val menuView = findViewById<View>(menuItemId)
    val largeTextView = menuView.findViewById<TextView>(R.id.largeLabel)
    largeTextView?.setTextStyle(style)
}

fun BottomNavigationView.setItemText(menuItemId: Int, text: String) {
    val menuView = findViewById<View>(menuItemId)
    val smallTextView = menuView.findViewById<TextView>(R.id.smallLabel)
    val largeTextView = menuView.findViewById<TextView>(R.id.largeLabel)
    smallTextView?.text = text
    largeTextView?.text = text
}
