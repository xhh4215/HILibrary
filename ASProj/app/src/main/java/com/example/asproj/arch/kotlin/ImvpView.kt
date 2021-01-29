package com.example.asproj.arch.kotlin

interface ImvpView<out Presenter:ImvpPresenter<ImvpView<Presenter>>> {
    val presenter: Presenter
}

