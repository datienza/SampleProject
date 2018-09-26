package com.farmdrop.customer

fun safeDisplayCmsValue(cmsValue: String?, displayLocalCopy: () -> Unit, displayCmsCopy: (value: String) -> Unit) {
    if (cmsValue == null || cmsValue.isEmpty()) {
        displayLocalCopy.invoke()
    } else {
        displayCmsCopy.invoke(cmsValue)
    }
}
