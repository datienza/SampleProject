package com.farmdrop.customer.ui.common

import android.app.Dialog
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import uk.co.farmdrop.library.ui.base.BaseDialogFragment

abstract class BaseNoTitleDialogFragment : BaseDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        // request a window without the title
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()

        dialog?.let {
            it.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
    }
}