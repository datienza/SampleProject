package com.farmdrop.customer.extensions

import android.content.Intent
import android.graphics.Point
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) =
        supportFragmentManager.inTransaction({ add(frameId, fragment) })

fun AppCompatActivity.addAnimatedFragment(fragment: Fragment, frameId: Int, enter: Int, exit: Int, popEnter: Int, popExit: Int) =
        supportFragmentManager.inTransaction({ add(frameId, fragment) }, enter, exit, popEnter, popExit)

fun AppCompatActivity.replaceAnimatedFragment(fragment: Fragment, frameId: Int, enter: Int, exit: Int, popEnter: Int, popExit: Int) =
        supportFragmentManager.inTransaction({ replace(frameId, fragment) }, enter, exit, popEnter, popExit)

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) =
        supportFragmentManager.inTransaction({ replace(frameId, fragment) })

fun AppCompatActivity.startActivityWithoutAnimation(intent: Intent) {
    startActivity(intent)
    overridePendingTransition(0, 0)
}

fun AppCompatActivity.getScreenDimensionsInPixels(): Point {
    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)
    return Point(displayMetrics.widthPixels, displayMetrics.heightPixels)
}