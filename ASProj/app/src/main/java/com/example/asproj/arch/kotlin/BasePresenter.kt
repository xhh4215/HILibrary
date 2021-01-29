package com.example.asproj.arch.kotlin

abstract class BasePresenter<out V : ImvpView<BasePresenter<V>>> : ImvpPresenter<V> {
    override lateinit var view: @UnsafeVariance V
}