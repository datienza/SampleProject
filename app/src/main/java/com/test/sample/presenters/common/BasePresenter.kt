package com.test.sample.presenters.common

import com.test.sample.ui.common.MvpView
import com.test.sample.ui.common.Presenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<T : MvpView> : Presenter<T> {

    var mvpView: T? = null
        private set

    protected var compositeDisposable: CompositeDisposable? = null

    val isViewAttached: Boolean
        get() = this.mvpView != null

    val isDisposableReady: Boolean
        get() = this.compositeDisposable != null

    override fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    override fun detachView() {
        this.mvpView = null
    }

    fun checkViewAttached() {
        if (!this.isViewAttached) {
            throw BasePresenter.MvpViewNotAttachedException()
        }
    }

    fun addDisposable(newDisposable: Disposable?) {
        if (newDisposable != null && this.compositeDisposable != null) {
            this.compositeDisposable!!.add(newDisposable)
        } else if (newDisposable != null) {
            this.compositeDisposable = CompositeDisposable()
            this.compositeDisposable!!.add(newDisposable)
        }
    }

    fun destroyAllDisposables() {
        if (this.compositeDisposable != null) {
            this.compositeDisposable!!.dispose()
            this.compositeDisposable = null
        }
    }

    class MvpViewNotAttachedException : RuntimeException("Please call Presenter.attachView(MvpView) before requesting data to the Presenter")
}
