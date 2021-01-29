package com.example.asproj.arch.kotlin

import android.text.TextUtils

class LoginPresenter : BasePresenter<LoginActivity>() {

    fun login(name: String, password: String) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
            view.onLoginSuccess()
        } else {
            view.onLoginFail()
        }
    }

}