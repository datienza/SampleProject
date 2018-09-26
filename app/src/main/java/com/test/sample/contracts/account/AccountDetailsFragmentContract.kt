package com.farmdrop.customer.contracts.account

import uk.co.farmdrop.library.ui.base.MvpView

interface AccountDetailsFragmentContract {
    interface View : MvpView {
        fun showEnterYourNameText()
        fun showName(firstName: String, lastName: String)
        fun showContactDetails(phone: String, email: String)
        fun showEmail(email: String)
        fun showPhone(phone: String)
        fun showEnterYourContactDetailsText()
        fun setReceiveNewsletterChecked(isChecked: Boolean)
        fun setDefaultNewsletterText()
        fun setNewsletterText(text: String)
    }
}
