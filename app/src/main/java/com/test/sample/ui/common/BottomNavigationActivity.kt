package com.farmdrop.customer.ui.common

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.view.MenuItem
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.common.BottomNavigationActivityContract
import com.farmdrop.customer.di.app.AppModule
import com.farmdrop.customer.extensions.startActivityWithoutAnimation
import com.farmdrop.customer.presenters.common.BottomNavigationActivityPresenter
import com.farmdrop.customer.ui.account.AccountActivity
import com.farmdrop.customer.ui.basket.BasketActivity
import com.farmdrop.customer.ui.home.BasketInitErrorDialog
import com.farmdrop.customer.ui.home.HomeActivity
import com.farmdrop.customer.ui.products.ProductsActivity
import com.farmdrop.customer.utils.analytics.AnalyticsEvents
import com.farmdrop.customer.utils.analytics.AnalyticsHelper
import kotlinx.android.synthetic.main.bottom_navigation_view.bottomNavigationView
import uk.co.farmdrop.library.utils.Constants.ZERO
import java.text.DecimalFormat
import javax.inject.Inject
import javax.inject.Named

private const val TELEPHONE_URI = "tel:"

/**
 * Base activity class to provide the app bottom navigation
 */
abstract class BottomNavigationActivity<P : BottomNavigationActivityPresenter<V>, V : BottomNavigationActivityContract.View> : BaseSearchActivity<P, V>(), BottomNavigationActivityContract.View {

    @Inject
    lateinit var bottomNavigationActivityTabsManager: BottomNavigationActivityTabsManager

    @field:Named(AppModule.NAME_PRICE_DECIMAL_FORMAT)
    @Inject
    lateinit var priceFormat: DecimalFormat

    protected fun initBottomNavigation(selectedItemId: Int) {
        bottomNavigationView.disableShiftMode()
        bottomNavigationView.selectedItemId = selectedItemId
        bottomNavigationView.setSelectedViewTextStyle(selectedItemId, Typeface.BOLD)
        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem ->
            presenter.navigateToTab(bottomNavigationView.selectedItemId, item.itemId)
            false
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.showCurrentBasketStatus()
        presenter.listenForBasketUpdateEvents()
    }

    protected fun openNewActivityForCurrentTab(intent: Intent) {
        bottomNavigationActivityTabsManager.saveIntent(bottomNavigationView.selectedItemId, intent)
        startActivityWithoutAnimation(intent)
    }

    override fun launchSavedIntent(destTabId: Int) {
        val intent = bottomNavigationActivityTabsManager.getSavedIntent(destTabId)
        if (intent != null) {
            startActivityWithoutAnimation(intent)
        }
    }

    override fun launchDefaultIntent(destTabId: Int) {
        var intent: Intent? = null

        when (destTabId) {
            R.id.action_discover -> {
                intent = HomeActivity.getStartingIntent(applicationContext)
                AnalyticsHelper.trackEventWithDataSource(this, AnalyticsEvents.TAB_SELECTED_EVENT, AnalyticsEvents.SECTION_PROPERTY, AnalyticsEvents.HOME_VALUE)
            }
            R.id.action_products -> {
                intent = ProductsActivity.getStartingIntent(applicationContext)
                AnalyticsHelper.trackEventWithDataSource(this, AnalyticsEvents.TAB_SELECTED_EVENT, AnalyticsEvents.SECTION_PROPERTY, AnalyticsEvents.PRODUCTS_VALUE)
            }
            // TODO: Put it back for v2
//            R.id.action_recipes -> {
//                intent = RecipesActivity.getStartingIntent(applicationContext)
//                AnalyticsHelper.trackEventWithDataSource(this, AnalyticsEvents.TAB_SELECTED_EVENT, AnalyticsEvents.SECTION_PROPERTY, AnalyticsEvents.RECIPES_VALUE)
//            }
            R.id.action_account -> {
                intent = AccountActivity.getStartingIntent(applicationContext)
                AnalyticsHelper.trackEventWithDataSource(this, AnalyticsEvents.TAB_SELECTED_EVENT, AnalyticsEvents.SECTION_PROPERTY, AnalyticsEvents.MY_ACCOUNT_VALUE)
            }
            R.id.action_basket -> {
                intent = BasketActivity.getStartingIntent(applicationContext)
                AnalyticsHelper.trackEventWithDataSource(this, AnalyticsEvents.TAB_SELECTED_EVENT, AnalyticsEvents.SECTION_PROPERTY, AnalyticsEvents.BASKET_VALUE)
            }
        }
        if (intent != null) {
            startActivityWithoutAnimation(intent)
        }
    }

    override fun finishActivity() {
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            presenter.navigateBack()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
        presenter.clearBasketUpdateEventDisposable()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        presenter.navigateBack()
    }

    override fun updateBasketItem(amount: Float, nbItems: Int) {
        val formattedText = getString(R.string.price, priceFormat.format(amount))
        bottomNavigationView.setItemText(R.id.action_basket, formattedText)
        bottomNavigationView.setBadgeValueFromId(R.id.action_basket, nbItems)
    }

    override fun displayBasketAsEmpty() {
        bottomNavigationView.setItemText(R.id.action_basket, getString(R.string.basket))
        bottomNavigationView.setBadgeValueFromId(R.id.action_basket, ZERO)
    }

    override fun displayBasketInitError() = BasketInitErrorDialog(this, ::dialPhoneNumber).show()

    private fun dialPhoneNumber() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(TELEPHONE_URI + resources.getString(R.string.farmdrop_phone_number)))
        startActivity(intent)
    }
}
