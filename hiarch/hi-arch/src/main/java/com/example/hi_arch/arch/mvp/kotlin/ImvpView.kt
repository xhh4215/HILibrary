package com.example.hi_arch.arch.mvp.kotlin

interface ImvpView<out Presenter: ImvpPresenter<ImvpView<Presenter>>> {
    val presenter: Presenter
}

