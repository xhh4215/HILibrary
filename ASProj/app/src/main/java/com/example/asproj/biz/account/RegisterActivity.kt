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
import com.example.asproj.databinding.ActivityRegisterBinding
import com.example.asproj.http.api.ApiFactory
import com.example.asproj.http.api.accountapi.AccountApi
import com.example.common.ui.component.HiBaseActivity
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.callback.HiCallBack
import com.example.hi_library.utils.HiStatusBarUtil.setStatusBar

/****
 * @data 2020年1月12日
 * @description 当前页面是实现注册的功能的用来注册用户
 * @author 栾桂明
 */
@Route(path = "/account/registration")
class RegisterActivity : HiBaseActivity() {
    //注册界面的布局绑定对象
    private lateinit var registerBinding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //适配状态栏
        setStatusBar(this, true, Color.WHITE, false)
        //初始化布局绑定对象
        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        //返回按钮
        registerBinding.actionBack.setOnClickListener {
            onBackPressed()
        }
        //  4290  4449783
        registerBinding.actionSubmit.setOnClickListener {
            submit()
        }
    }

    /***
     * 提交注册信息的方法
     */
    private fun submit() {
        val orderId = registerBinding.inputItemOrderId.editText.text.toString()
        val moocId = registerBinding.inputItemMoocId.editText.text.toString()
        val username = registerBinding.inputItemUsername.editText.text.toString()
        val password = registerBinding.inputItemPassword.editText.text.toString()
        val repassword = registerBinding.inputItemRepassword.editText.text.toString()
        if (TextUtils.isEmpty(orderId) or TextUtils.isEmpty(moocId) or TextUtils.isEmpty(username)
            or TextUtils.isEmpty(password) or TextUtils.isEmpty(repassword) or
            !TextUtils.equals(password, repassword)
        ) {
            return
        }

        ApiFactory.create(AccountApi::class.java).register(username, password, moocId, orderId)
            .enqueue(
                object : HiCallBack<String> {
                    override fun onSuccess(response: HiResponse<String>) {
                        if (response.code == HiResponse.SUCCESS) {
                            showToast("${response.msg}")
                            var intent = Intent()
                            intent.putExtra("username", username)
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        } else {
                            response.msg?.let {
                                showToast("注册失败$it")
                            }
                        }
                    }

                    override fun onFailed(throwable: Throwable) {
                        showToast(throwable.message!!)
                    }

                })
    }

    /***
     * 吐司提示
     */
    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

    }
}