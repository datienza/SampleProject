package com.farmdrop.customer.ui.common

import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.farmdrop.customer.R
import com.farmdrop.customer.extensions.addFragment
import uk.co.farmdrop.library.BaseApplication
import uk.co.farmdrop.library.di.activity.HasActivitySubcomponentBuilders

abstract class BaseActivity : AppCompatActivity() {
    protected var mCurrentFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        intent?.extras?.let {
            retrieveIntentBundle(it)
        }

        setupActivityComponent()
        super.onCreate(savedInstanceState)
    }

    protected open fun retrieveIntentBundle(@Suppress("UNUSED_PARAMETER") bundle: Bundle) {}

    private fun setupActivityComponent() {
        injectMembers(BaseApplication.get(this))
    }

    protected abstract fun injectMembers(hasActivitySubComponentBuilders: HasActivitySubcomponentBuilders)

    protected fun initFragment() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            createFragment()
            launchFragment()
        } else if (fragment is BaseFragment) {
            mCurrentFragment = fragment
        }
    }

    protected abstract fun createFragment()

    private fun launchFragment() {
        val currentFragment = mCurrentFragment
        if (currentFragment != null) {
            addFragment(currentFragment, R.id.fragment_container)
        } else {
            throw RuntimeException("no fragment set")
        }
    }

    protected open fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTextAppearance)
    }

    protected fun setHomeAsUpEnabled(@DrawableRes arrowBackDrawable: Int) {
        supportActionBar?.let {
            it.setHomeAsUpIndicator(arrowBackDrawable)
            it.setDisplayHomeAsUpEnabled(true)
        }
        setActionBarBackButtonContentDescription()
    }

    protected fun setActionBarBackButtonContentDescription() {
        supportActionBar?.setHomeActionContentDescription(R.string.content_description_action_bar_back_button)
    }
}
