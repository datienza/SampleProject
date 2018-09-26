package com.farmdrop.customer.contracts.account

import android.support.annotation.StringRes
import com.farmdrop.customer.contracts.common.BottomNavigationActivityContract
import com.farmdrop.customer.data.model.order.OrderData
import com.farmdrop.customer.utils.cms.EthicalReminder
import uk.co.farmdrop.library.resolution.Resolution

/**
 * Define contract methods between AccountActivity & AccountActivityPresenter
 */
interface AccountActivityContract {

    interface View : BottomNavigationActivityContract.View {
        fun showLogInSelectFragment()
        fun showLogOutButton()
        fun hideLogOutButton()
        fun showLogInLayout()
        fun hideLogInLayout()
        fun showUserDetailsLayout()
        fun hideUserDetailsLayout()
        fun showUserName(userName: String)
        fun showUserEmail(userEmail: String)
        fun showUserAddress(userAddress: String)
        fun hideUserAddress()
        fun getResolution(): Resolution
        fun getUserAddress(address1: String, zipcode: String): String
        fun showUserCardDetails(brand: String, last4: String)
        fun hideUserCardDetails()

        fun showOrdersLayout()
        fun hideOrdersLayout()
        fun showNoOrderLayout()
        fun showNoUpcomingOrderLayout()
        fun showOrderLayout(orderData: OrderData)
        fun clearLastOrdersLayout()

        fun showLoadingOrdersProgressBar()
        fun hideLoadingOrdersProgressBar()

        fun showEthicalReminderRecyclerView()
        fun hideEthicalReminderRecyclerView()
        fun addEthicalReminders(ethicalReminders: List<EthicalReminder>)

        fun setToolbar(@StringRes title: Int)
        fun showAccountLayout()
        fun hideAccountLayout()
        fun removeFragment()
        fun showOrdersFragment()
        fun showFAQsFragment()
        fun showHowItWorksFragment()
        fun showHowWeSourceFragment()

        fun trackUserLogOutAnalyticsEvent()
    }
}
