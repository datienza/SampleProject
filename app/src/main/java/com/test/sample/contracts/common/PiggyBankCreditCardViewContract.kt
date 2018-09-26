package com.farmdrop.customer.contracts.common

import uk.co.farmdrop.library.ui.base.MvpView

interface PiggyBankCreditCardViewContract {
    interface View : MvpView {
        fun showContent(credit: Int)
        fun showCredit(creditAvailable: Double)
        fun shareReferralMessage(giverAmount: Int, referralBaseUrl: String)
        fun showInviteFriendsError()
    }
}
