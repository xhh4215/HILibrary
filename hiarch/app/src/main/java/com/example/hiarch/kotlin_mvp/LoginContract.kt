package com.example.hiarch.kotlin_mvp

import android.text.TextUtils
import com.example.hi_arch.arch.mvp.kotlin.BaseActivity
import com.example.hi_arch.arch.mvp.kotlin.BasePresenter


class MvpInKotlinPresenter : BasePresenter<MvpInKotlinActivity>() {
    fun login(name: String, password: String) {
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
            view.onLoginSuccess("成功")
        } else {
            view.onLoginFail("失败")
        }
    }


    abstract class MvpInKotlinView : BaseActivity<MvpInKotlinPresenter>() {
        abstract fun onLoginSuccess(value: String)

        abstract fun onLoginFail(error: String)
    }
}





