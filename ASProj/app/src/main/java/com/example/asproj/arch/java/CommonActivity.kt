package com.example.asproj.arch.java

import android.os.Bundle
import android.util.Log
import com.example.asproj.R

class CommonActivity : BaseActivity<CommonPresenterImp>(),
    CommonContract.View {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common)
        mPresenter.HttpCommon("sssss")
        showLoading()
    }

    override fun showLoading() {
        Log.d("tag", "显示加载")
    }

    override fun onCommonFail(error: String?) {
        hideLoading()
    }

    override fun onCommonSuccess(value: String?) {
        hideLoading()
    }

    override fun hideLoading() {
        Log.d("tag", "隐藏加载")
    }
}