package com.example.asproj.biz.account

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
 import com.example.asproj.R
import com.example.asproj.databinding.ActivityLoginBinding
import com.example.asproj.http.api.accountapi.AccountApi
import com.example.asproj.http.api.ApiFactory
import com.example.asproj.rote.HiRoute
import com.example.common.ui.component.HiBaseActivity
import com.example.common.utils.SPUtil
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.callback.HiCallBack
import com.example.hi_library.utils.HiStatusBarUtil
import com.example.hi_library.utils.HiStatusBarUtil.setStatusBar

/****
 * @date 2020年1月12日
 * @description 当前界面是用户登录的功能的实现
 * @author 栾桂明
 */
@Route(path = "/account/login")
class LoginActivity : HiBaseActivity() {
    //当前界面的布局绑定对象
    private lateinit var loginBinding: ActivityLoginBinding

    //注册结果的request_code
    companion object {
        const val REGISTER_ACTIVITY_CODE = 12

    }

    /***
     * 在Activity第一次初始化回调的方法
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //适配状态栏
        setStatusBar(this, true, Color.WHITE, false)
        //布局绑定对象的初始化
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        //返回按钮
        loginBinding.actionBack.setOnClickListener {
            onBackPressed()
        }
        //注册
        loginBinding.actionRegister.setOnClickListener {
            goRegistration()
        }
        //登录
        loginBinding.actionLogin.setOnClickListener {
            goLogin()
        }

    }

    /***
     * 登录逻辑的实现
     */
    private fun goLogin() {
        //用户名
        val name = loginBinding.inputItemUsername.editText.text
        //密码
        val password = loginBinding.inputItemPassword.editText.text
        //密码和用户名合法性检测
        if (TextUtils.isEmpty(name) or (TextUtils.isEmpty(password))) {
            return
        }
        //登录的api的处理
        ApiFactory.create(AccountApi::class.java).login(name.toString(), password.toString())
            .enqueue(object : HiCallBack<String> {
                override fun onSuccess(response: HiResponse<String>) =
                    if (response.code == HiResponse.SUCCESS) {
                        //usermanager
                        val data = response.data
                        SPUtil.putString("boarding-pass", data!!)
                        setResult(Activity.RESULT_OK, Intent())
                        finish()
                    } else {
                        showToast(getString(R.string.login_fail) + "${response.msg}")
                    }

                override fun onFailed(throwable: Throwable) {
                    showToast(getString(R.string.login_fail) + "${throwable.message}")

                }

            })
    }

    /***
     * 注册逻辑的实现
     */
    private fun goRegistration() {
        HiRoute.startActivity(this, HiRoute.Destination.ACCOUNT_REGISTER)
    }

    /***
     * 注册界面返回的数据的处理
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((resultCode == Activity.RESULT_OK) and (data != null) and (requestCode == REGISTER_ACTIVITY_CODE)) {
            val username = data?.getStringExtra("username") ?: "无名氏"
            if (!TextUtils.isEmpty(username)) {
                loginBinding.inputItemUsername.editText.setText(username)
            }
        }
    }

    /****
     * 吐司提示
     */
    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

    }
}