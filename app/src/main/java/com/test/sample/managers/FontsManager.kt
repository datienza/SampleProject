package com.farmdrop.customer.managers

import android.content.Context
import android.graphics.Typeface
import android.support.annotation.FontRes
import android.support.v4.content.res.ResourcesCompat
import android.util.SparseArray

class FontsManager(private val mAppContext: Context) {

    private val mFonts = SparseArray<Typeface>()

    fun getFont(@FontRes fontId: Int): Typeface? {
        var font = mFonts[fontId]

        if (font == null) {
            font = ResourcesCompat.getFont(mAppContext, fontId)
            if (font != null) {
                mFonts.put(fontId, font)
            }
        }
        return font
    }
}