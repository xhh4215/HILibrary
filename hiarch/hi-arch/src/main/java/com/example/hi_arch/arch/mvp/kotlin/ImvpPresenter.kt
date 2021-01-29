package com.example.hi_arch.arch.mvp.kotlin


interface ImvpPresenter<out View : ImvpView<ImvpPresenter<View>>> {
    val view: View
}