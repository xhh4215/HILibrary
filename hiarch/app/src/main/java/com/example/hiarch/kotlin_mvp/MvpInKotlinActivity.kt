package com.example.hiarch.kotlin_mvp

import android.os.Bundle
import android.widget.Toast
import com.example.hiarch.R

class MvpInKotlinActivity : MvpInKotlinPresenter.MvpInKotlinView() {
    override fun onLoginSuccess(value: String) {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
    }

    override fun onLoginFail(error: String) {
        Toast.makeText(this, "登录失败", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvp_in_kotlin)
        presenter.login("lgm" , "123456")
    }


}