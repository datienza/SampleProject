package com.farmdrop.customer.ui.common

import android.content.Context
import android.content.res.TypedArray
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.farmdrop.customer.R
import uk.co.farmdrop.library.utils.Constants.INVALID_ID

private const val FIELD_MENU_VIEW = "menuView"

class BottomNavigationWithBadgeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        BottomNavigationView(context, attrs, defStyleAttr) {

    private val bottomNavigationMenuView: BottomNavigationMenuView

    private var badgeLayoutResourceId: Int = INVALID_ID

    private var itemWithBadgeIndex: Int = INVALID_ID

    private var itemsWithBadgeIndexes: IntArray? = null

    private var badgeHorizontalMarginFromCenter = 0

    private var badgeVerticalMarginFromCenter = 0

    init {
        val field = BottomNavigationView::class.java.getDeclaredField(FIELD_MENU_VIEW)
        field.isAccessible = true
        bottomNavigationMenuView = field.get(this) as BottomNavigationMenuView

        if (attrs != null) {
            val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.BottomNavigationWithBadgeView, 0, 0)
            getAttributes(attributes)
            attributes.recycle()
        }

        if (badgeLayoutResourceId != INVALID_ID) {
            inflateBadgeViews()
        }
    }

    private fun getAttributes(attributes: TypedArray) {
        badgeLayoutResourceId = attributes.getResourceId(R.styleable.BottomNavigationWithBadgeView_itemBadgeLayout, INVALID_ID)

        val itemWithBadgeIndexResource = attributes.getResourceId(R.styleable.BottomNavigationWithBadgeView_itemWithBadgeIndex, INVALID_ID)
        if (itemWithBadgeIndexResource != INVALID_ID) {
            itemWithBadgeIndex = resources.getInteger(itemWithBadgeIndexResource)
        }
        val itemsWithBadgeIndexesResource = attributes.getResourceId(R.styleable.BottomNavigationWithBadgeView_itemWithBadgeIndexes, INVALID_ID)
        if (itemsWithBadgeIndexesResource != INVALID_ID) {
            itemsWithBadgeIndexes = resources.getIntArray(itemsWithBadgeIndexesResource)
        }

        badgeHorizontalMarginFromCenter = attributes.getDimensionPixelSize(R.styleable.BottomNavigationWithBadgeView_itemBadgeHorizontalMarginFromCenter, 0)
        badgeVerticalMarginFromCenter = attributes.getDimensionPixelSize(R.styleable.BottomNavigationWithBadgeView_itemBadgeVerticalMarginFromCenter, 0)
    }

    private fun inflateBadgeViews() {
        // post initialization work, to make sure navigation view size has been calculated
        // we need to use that size to position the badge correctly.
        post {
            if (isIndexValid(itemWithBadgeIndex)) {
                inflateBadgeViewForIndex(itemWithBadgeIndex)
            }
            itemsWithBadgeIndexes?.let { itemsWithBadgeIndexes ->
                itemsWithBadgeIndexes.filter { it != itemWithBadgeIndex }.forEach { inflateBadgeViewForIndex(it) }
            }
        }
    }

    private fun inflateBadgeViewForIndex(index: Int) {
        val itemView = bottomNavigationMenuView.getChildAt(index) as BottomNavigationItemView

        val layoutInflater = LayoutInflater.from(context)
        val badgeView = layoutInflater.inflate(badgeLayoutResourceId, itemView, false)
        badgeView.visibility = View.GONE

        val defaultMargin = resources.getDimensionPixelSize(android.support.design.R.dimen.design_bottom_navigation_margin)
        val leftMargin = itemView.width / 2 - defaultMargin + badgeHorizontalMarginFromCenter
        val topMargin = itemView.height / 2 - defaultMargin + badgeVerticalMarginFromCenter

        val layoutParam = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        layoutParam.setMargins(leftMargin, topMargin, 0, 0)
        itemView.addView(badgeView, layoutParam)
    }

    fun setBadgeValueFromIndex(index: Int, count: Int) {
        if (isIndexValid(index)) {
            val bottomNavigationItemView = bottomNavigationMenuView.getChildAt(index)
            if (bottomNavigationItemView != null) {
                // post text update, to make sure it's called after inflateBadgeViews() and to
                // make sure it runs on the UI thread
                post {
                    val badgeView = bottomNavigationItemView.findViewById<View>(R.id.bottomNavigationViewBadgeTextView)
                    if (badgeView != null && badgeView is TextView) {
                        setBadgeViewValue(badgeView, count)
                    }
                }
            }
        }
    }

    fun setBadgeValueFromId(id: Int, count: Int) {
        val bottomNavigationItemView = bottomNavigationMenuView.findViewById<BottomNavigationItemView>(id)
        if (bottomNavigationItemView != null) {
            // post text update, to make sure it's called after inflateBadgeViews() and to
            // make sure it runs on the UI thread
            post {
                val badgeView = bottomNavigationItemView.findViewById<View>(R.id.bottomNavigationViewBadgeTextView)
                if (badgeView != null && badgeView is TextView) {
                    setBadgeViewValue(badgeView, count)
                }
            }
        }
    }

    private fun setBadgeViewValue(badgeView: TextView, count: Int) {
        if (count > 0) {
            badgeView.text = count.toString()
            badgeView.visibility = View.VISIBLE
        } else {
            badgeView.visibility = View.GONE
        }
    }

    private fun isIndexValid(index: Int) = index >= 0 && index < bottomNavigationMenuView.childCount
}