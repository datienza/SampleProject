package com.farmdrop.customer.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.farmdrop.customer.R
import com.farmdrop.customer.contracts.home.HomeActivityContract
import com.farmdrop.customer.data.model.order.OrderData
import com.farmdrop.customer.presenters.home.HomeActivityPresenter
import com.farmdrop.customer.ui.common.BottomNavigationActivity
import com.farmdrop.customer.ui.delivery.DATE_PICKER_ACTIVITY_REQUEST_CODE
import com.farmdrop.customer.ui.delivery.DatePickerActivity
import com.farmdrop.customer.ui.orderdetails.OrderDetailsActivity
import com.farmdrop.customer.ui.producers.ProducerDetailsActivity
import com.farmdrop.customer.ui.products.ProductDetailsActivity
import com.farmdrop.customer.ui.session.SessionActivity
import com.farmdrop.customer.ui.session.SessionActivity.Companion.SESSION_ACTIVITY_REQUEST_CODE
import com.farmdrop.customer.utils.analytics.AnalyticsEvents
import kotlinx.android.synthetic.main.activity_home.orderLayout
import kotlinx.android.synthetic.main.activity_home.upcomingOrderView
import kotlinx.android.synthetic.main.login_layout.logInButton
import kotlinx.android.synthetic.main.login_layout.loginLayoutTitle
import kotlinx.android.synthetic.main.login_layout.signUpNowLayout
import kotlinx.android.synthetic.main.login_layout.signUpNowText
import kotlinx.android.synthetic.main.toolbar_search.searchView
import kotlinx.android.synthetic.main.toolbar_search.toolbar
import kotlinx.android.synthetic.main.view_current_delivery_slot.currentDeliverySlotView
import kotlinx.android.synthetic.main.view_spotlight.spotlightView
import uk.co.farmdrop.library.di.activity.HasActivitySubcomponentBuilders
import uk.co.farmdrop.library.di.view.HasViewSubcomponentBuilders
import uk.co.farmdrop.library.di.view.ViewComponent
import uk.co.farmdrop.library.di.view.ViewComponentBuilder
import uk.co.farmdrop.library.di.view.ViewModule
import uk.co.farmdrop.library.utils.analytics.AnalyticsHelper
import javax.inject.Inject
import javax.inject.Provider

class HomeActivity : BottomNavigationActivity<HomeActivityPresenter, HomeActivityContract.View>(),
        HomeActivityContract.View,
        OnSpotlightViewClickedListener,
        HasViewSubcomponentBuilders {

    companion object {
        private const val EXTRA_APPLICATION_STARTED = "application_started"

        fun getStartingIntent(context: Context, applicationStarted: Boolean = false): Intent {
            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtra(EXTRA_APPLICATION_STARTED, applicationStarted)
            return intent
        }
    }

    @Inject
    lateinit var viewComponentBuilders: Map<Class<out View>, @JvmSuppressWildcards Provider<ViewComponentBuilder<*, *>>>

    private var applicationStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        presenter.attachView(this)
        initToolbar()
        initBottomNavigation(R.id.action_discover)
        initSearchView(searchView)
        initCurrentDeliverySlotView()

        presenter.initBasket()
        presenter.reinitialiseStripeUserSession()

        signUpNowText.setOnClickListener {
            showSignUpSelectedFragment()
        }

        logInButton.setOnClickListener {
            presenter.onLogInOutPressed()
        }

        spotlightView.setOnSpotlightViewClickedListener(this)
    }

    override fun retrieveIntentBundle(bundle: Bundle) {
        super.retrieveIntentBundle(bundle)
        applicationStarted = bundle.getBoolean(EXTRA_APPLICATION_STARTED)
    }

    override fun onStart() {
        super.onStart()
        presenter.updateUiAccordingToUserLoginState()
    }

    override fun onResume() {
        super.onResume()
        // TODO put this into a recyclerView when we decide how to do shopfront
        currentDeliverySlotView.init()
        spotlightView.bind()
        upcomingOrderView.resumeOrderTimeRemainingCountdown()
    }

    override fun onPause() {
        super.onPause()
        currentDeliverySlotView.destroyAllDisposables()
        spotlightView.destroyAllDisposables()
        applicationStarted = false
        upcomingOrderView.pauseOrderTimeRemainingCountdown()
    }

    override fun onStop() {
        super.onStop()
        presenter.destroyAllDisposables()
        upcomingOrderView.destroyAllDisposables()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
        spotlightView.detachView()
        upcomingOrderView.detachView()
    }

    override fun injectMembers(hasActivitySubComponentBuilders: HasActivitySubcomponentBuilders) {
        (hasActivitySubComponentBuilders
                .getActivityComponentBuilder(HomeActivity::class.java) as HomeActivityComponent.Builder)
                .activityModule(HomeActivityComponent.HomeActivityModule(this))
                .build()
                .injectMembers(this)
    }

    override fun createFragment() {
    }

    private fun initToolbar() {
        setToolbar(toolbar)
    }

    override fun getViewComponentBuilder(viewClass: Class<out View>?): ViewComponentBuilder<out ViewModule<*>, out ViewComponent<*>> =
            viewComponentBuilders[viewClass]!!.get()

    private fun showSignUpSelectedFragment() {
        val intent = SessionActivity.getStartingIntent(this, SessionActivity.SIGN_UP)
        startActivityForResult(intent, SESSION_ACTIVITY_REQUEST_CODE)
    }

    override fun openSessionActivity() {
        val intent = SessionActivity.getStartingIntent(this, SessionActivity.LOG_IN)
        startActivityForResult(intent, SESSION_ACTIVITY_REQUEST_CODE)
    }

    override fun showLogOutButtonText() {
        logInButton.text = getString(R.string.log_out)
        signUpNowLayout.visibility = View.GONE
        loginLayoutTitle.visibility = View.GONE
    }

    override fun showLogInButtonText() {
        logInButton.text = getString(R.string.log_in)
        signUpNowLayout.visibility = View.VISIBLE
        loginLayoutTitle.visibility = View.VISIBLE
    }

    private fun initCurrentDeliverySlotView() {
        currentDeliverySlotView.setChevronImageUp(false)
        currentDeliverySlotView.setOnClickListener {
            val intent = DatePickerActivity.getStartingIntent(this, AnalyticsEvents.ORIGIN_SHOP_FRONT)
            startActivityForResult(intent, DATE_PICKER_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onProducerSpotlightClicked(producerId: Int) {
        val intent = ProducerDetailsActivity.getStartingIntent(this, producerId)
        startActivity(intent)
    }

    override fun onProductSpotlightClicked(productId: Int) {
        val intent = ProductDetailsActivity.getStartingIntent(this, productId)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if ((requestCode == SESSION_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) || requestCode == DATE_PICKER_ACTIVITY_REQUEST_CODE) {
            presenter.updateUiAccordingToUserLoginState()
        }
    }

    override fun showDeliverySlotView() {
        currentDeliverySlotView.visibility = View.VISIBLE
        currentDeliverySlotView.displayCurrentSlot()
    }

    override fun hideDeliverySlotView() {
        currentDeliverySlotView.visibility = View.GONE
    }

    override fun showOrder(orderData: OrderData) {
        orderLayout.visibility = View.VISIBLE
        upcomingOrderView.bind(orderData)
        upcomingOrderView.setOnClickListener {
            startActivity(OrderDetailsActivity.getStartingIntent(this, orderData.id))
        }
    }

    override fun hideOrderLayout() {
        orderLayout.visibility = View.GONE
    }

    override fun trackUserLogOutAnalyticsEvent() {
        AnalyticsHelper.resetUser(this)
    }
}
