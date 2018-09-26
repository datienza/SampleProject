package com.farmdrop.customer.extensions

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import uk.co.farmdrop.library.ui.base.BaseDialogFragment

/**
 * Provide method extensions for FragmentManager class.
 * Created by datienza on 22/08/2017.
 */

/*
* @param enter An animation or animator resource ID used for the enter animation on the
*              view of the fragment being added or attached.
* @param exit An animation or animator resource ID used for the exit animation on the
*             view of the fragment being removed or detached.
* @param popEnter An animation or animator resource ID used for the enter animation on the
*                 view of the fragment being readded or reattached caused by
*                 {@link FragmentManager#popBackStack()} or similar methods.
* @param popExit An animation or animator resource ID used for the enter animation on the
*                view of the fragment being removed or detached caused by
*                {@link FragmentManager#popBackStack()} or similar methods.
*/
inline fun FragmentManager.inTransaction(
    func: FragmentTransaction.() -> Unit,
    enter: Int = 0,
    exit: Int = 0,
    popEnter: Int = 0,
    popExit: Int = 0
): Int {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.setCustomAnimations(enter, exit, popEnter, popExit)
    fragmentTransaction.func()
    return fragmentTransaction.addToBackStack(null).commit()
}

/**
 * @return Returns the identifier of the committed transaction, as per
 * {@link FragmentTransaction#commit() FragmentTransaction.commit()}.
 */
fun <T : BaseDialogFragment> FragmentManager.showDialog(baseDialogFragment: T, tag: String): Int {
    // BaseDialogFragment.show() will take care of adding the fragment
    // in a transaction.  We also want to remove any currently showing
    // dialog, so make our own transaction and take care of that here.
    val ft = beginTransaction()
    val prev = findFragmentByTag(tag)
    if (prev != null) {
        ft.remove(prev)
    }
    ft.addToBackStack(null)

    // Show the dialog.
    return baseDialogFragment.show(ft, tag)
}