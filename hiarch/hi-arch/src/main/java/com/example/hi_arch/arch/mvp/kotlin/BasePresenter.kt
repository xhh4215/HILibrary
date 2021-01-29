package com.example.hi_arch.arch.mvp.kotlin

abstract class BasePresenter<out V : ImvpView<BasePresenter<V>>> :
    ImvpPresenter<V> {
    override lateinit var view: @UnsafeVariance V

}