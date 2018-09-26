package com.test.sample.ui.common

interface Presenter<V : MvpView> {
    fun attachView(var1: V)

    fun detachView()
}
