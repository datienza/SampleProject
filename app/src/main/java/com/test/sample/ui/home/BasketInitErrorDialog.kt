package com.farmdrop.customer.ui.home

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ForegroundColorSpan
import android.view.View
import com.farmdrop.customer.R
import com.farmdrop.customer.ui.session.BaseCustomDialog
import kotlinx.android.synthetic.main.dialog_basket_init_error.callBasketErrorText

class BasketInitErrorDialog(context: Context, private val onCall: () -> Unit) : BaseCustomDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_basket_init_error)
        setCanceledOnTouchOutside(false)
        setCancelable(false)

        val farmdropNumber = context.getString(R.string.farmdrop_phone_number)
        val callText = context.getString(R.string.dialog_call_basket_init_error)
        val displayString = callText + farmdropNumber
        val spannableString = SpannableString(displayString)
        val startIndex = displayString.indexOf(farmdropNumber)
        val endIndex = startIndex + farmdropNumber.length

        spannableString.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, R.color.farmdropGreen)), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(
                object : ClickableSpanWithoutUnderline() {
                    override fun onClick(view: View) { onCall() }
                },
                startIndex,
                endIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        callBasketErrorText.text = spannableString
        callBasketErrorText.isClickable = true
        callBasketErrorText.movementMethod = LinkMovementMethod.getInstance()
    }
}
