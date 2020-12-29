package com.example.asproj.biz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.example.asproj.R
import com.example.asproj.databinding.ActivityRegisterBinding
import com.example.asproj.http.AccountApi
import com.example.asproj.http.ApiFactory
import com.example.common.ui.component.HiBaseActivity
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.callback.HiCallBack
@Route(path = "/account/registration")
class RegisterActivity : HiBaseActivity() {
    private lateinit var registerBinding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        registerBinding.actionBack.setOnClickListener {
            onBackPressed()
        }
        /***
         * 4290
         * 4449783
         *
         */
        registerBinding.actionSubmit.setOnClickListener {
            submit()
        }
    }


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
                            setResult(Activity.RESULT_OK,intent)
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

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

    }
}