package com.example.asproj.arch.kotlin


interface ImvpPresenter<out View : ImvpView<ImvpPresenter<View>>> {
    val view: View
}