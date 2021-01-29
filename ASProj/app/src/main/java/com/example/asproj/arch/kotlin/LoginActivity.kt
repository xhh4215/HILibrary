package com.example.asproj.arch.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil.setContentView
import com.example.asproj.R

class LoginActivity : BaseActivity<LoginPresenter>(), LogInCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        presenter.login("sss", "sss")

    }

    override fun onLoginSuccess() {
        Log.d("tag", "登录成功")
    }

    override fun onLoginFail() {
        Log.d("tag", "登录失败")

    }


}