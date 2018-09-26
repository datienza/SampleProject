package com.farmdrop.customer.extensions

import android.support.design.widget.TextInputLayout
import android.text.TextUtils

fun TextInputLayout.clearError() {
    if (!TextUtils.isEmpty(error)) {
        error = null
        isErrorEnabled = false
    }
}