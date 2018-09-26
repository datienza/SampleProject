package com.farmdrop.customer.ui.common

import android.content.Context
import android.content.res.ColorStateList
import android.support.design.widget.TextInputLayout
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter
import android.support.v4.view.animation.FastOutSlowInInterpolator
import android.support.v4.widget.TextViewCompat
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.animation.Interpolator
import android.widget.EditText
import android.widget.TextView
import com.farmdrop.customer.R
import com.farmdrop.customer.utils.constants.UiConstants

class CustomTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    private val mFastOutSlowInInterpolator: Interpolator = FastOutSlowInInterpolator()

    private var mHelperText: CharSequence? = null
    private var mHelperTextColor: ColorStateList? = null
    private var mHelperTextEnabled: Boolean = false
    private var mErrorEnabled: Boolean = false
    private var mHelperView: TextView? = null
    private var mHelperTextAppearance: Int = R.style.HelperTextAppearance

    init {
        val a = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.CustomTextInputLayout, 0, 0)
        try {
            mHelperTextColor = a.getColorStateList(R.styleable.CustomTextInputLayout_helperTextColor)
            mHelperText = a.getText(R.styleable.CustomTextInputLayout_helperText)
        } finally {
            a.recycle()
        }
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        super.addView(child, params)
        if (child is EditText) {
            mHelperText?.let {
                if (!TextUtils.isEmpty(it)) {
                    helperText = it
                }
            }
        }
    }

    fun getHelperTextAppearance(): Int {
        return mHelperTextAppearance
    }

    fun setHelperTextAppearance(helperTextAppearanceResId: Int) {
        mHelperTextAppearance = helperTextAppearanceResId
    }

    override fun setHelperTextColor(helperTextColor: ColorStateList?) {
        mHelperTextColor = helperTextColor
    }

    override fun setHelperTextEnabled(enabled: Boolean) {
        if (mHelperTextEnabled == enabled) return
        if (enabled && mErrorEnabled) {
            isErrorEnabled = false
        }
        if (mHelperTextEnabled != enabled) {
            if (enabled) {
                val helperView = TextView(context)
                if (mHelperTextColor != null) {
                    helperView.setTextColor(mHelperTextColor)
                    TextViewCompat.setTextAppearance(helperView, mHelperTextAppearance)
                }
                helperView.visibility = INVISIBLE
                addView(helperView)
                editText?.paddingBottom?.let {
                    ViewCompat.setPaddingRelative(
                            helperView,
                            ViewCompat.getPaddingStart(editText as View),
                            0, ViewCompat.getPaddingEnd(editText as View),
                            it)
                }

                mHelperView = helperView
            } else {
                removeView(mHelperView)
                mHelperView = null
            }

            mHelperTextEnabled = enabled
        }
    }

    override fun setHelperText(helperText: CharSequence?) {
        mHelperText = helperText
        if (!mHelperTextEnabled) {
            if (TextUtils.isEmpty(mHelperText)) {
                return
            }
            isHelperTextEnabled = true
        }

        mHelperView?.let {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
            if (!TextUtils.isEmpty(mHelperText)) {
                it.text = mHelperText
                it.visibility = VISIBLE
                it.alpha = UiConstants.ALPHA_TRANSPARENT
                ViewCompat.animate(it)
                        .alpha(UiConstants.ALPHA_OPAQUE).setDuration(shortAnimTime.toLong())
                        .setInterpolator(mFastOutSlowInInterpolator)
                        .setListener(null).start()
            } else if (it.visibility == VISIBLE) {
                ViewCompat.animate(it)
                        .alpha(UiConstants.ALPHA_TRANSPARENT).setDuration(shortAnimTime.toLong())
                        .setInterpolator(mFastOutSlowInInterpolator)
                        .setListener(object : ViewPropertyAnimatorListenerAdapter() {
                            override fun onAnimationEnd(view: View) {
                                it.text = null
                                it.visibility = INVISIBLE
                            }
                        }).start()
            }
            sendAccessibilityEvent(AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED)
        }
    }

    override fun setErrorEnabled(enabled: Boolean) {
        if (mErrorEnabled == enabled) return
        mErrorEnabled = enabled
        if (enabled && mHelperTextEnabled) {
            isHelperTextEnabled = false
        }

        super.setErrorEnabled(enabled)

        mHelperText?.let {
            if (!(enabled || TextUtils.isEmpty(it))) {
                helperText = it
            }
        }
    }
}
