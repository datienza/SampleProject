package com.farmdrop.customer.ui.common

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.inputmethod.InputMethodManager
import uk.co.farmdrop.library.di.fragment.HasFragmentSubcomponentBuilders
import uk.co.farmdrop.library.extentions.hideSoftwareKeyboard

abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        arguments?.let {
            unpackArguments(it)
        }

        setupFragmentComponent()
        super.onCreate(savedInstanceState)
    }

    protected open fun unpackArguments(@Suppress("UNUSED_PARAMETER") bundle: Bundle) {}

    private fun setupFragmentComponent() {
        injectMembers(context as HasFragmentSubcomponentBuilders)
    }

    protected abstract fun injectMembers(hasFragmentSubcomponentBuilders: HasFragmentSubcomponentBuilders)

    protected fun hideSoftwareKeyboard() {
        activity?.let {
            val inputManager = it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftwareKeyboard(it)
        }
    }
}