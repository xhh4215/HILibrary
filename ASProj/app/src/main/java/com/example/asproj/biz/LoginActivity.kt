package com.example.asproj.biz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.example.asproj.R
import com.example.asproj.databinding.ActivityLoginBinding
import com.example.asproj.http.AccountApi
import com.example.asproj.http.ApiFactory
import com.example.common.ui.component.HiBaseActivity
import com.example.common.utils.SPUtil
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.callback.HiCallBack
import org.w3c.dom.Text
@Route(path = "/account/login")
class LoginActivity : HiBaseActivity() {
    private lateinit var loginBinding: ActivityLoginBinding

    companion object {
        const val REGISTER_ACTIVITY_CODE = 12

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginBinding.actionBack.setOnClickListener {
            onBackPressed()
        }
        loginBinding.actionRegister.setOnClickListener {
            goRegistration()
        }
        loginBinding.actionLogin.setOnClickListener {
            goLogin()
        }

    }

    private fun goLogin() {
        val name = loginBinding.inputItemUsername.editText.text
        val password = loginBinding.inputItemPassword.editText.text
        if (TextUtils.isEmpty(name) or (TextUtils.isEmpty(password))) {
            return
        }
        ApiFactory.create(AccountApi::class.java).login(name.toString(), password.toString())
            .enqueue(object : HiCallBack<String> {
                override fun onSuccess(response: HiResponse<String>) =
                    if (response.code == HiResponse.SUCCESS) {
                        showToast(getString(R.string.login_success))
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

    private fun goRegistration() {
        ARouter.getInstance().build("/account/registration").navigation(this,REGISTER_ACTIVITY_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if ((resultCode == Activity.RESULT_OK) and (data != null)and (requestCode==REGISTER_ACTIVITY_CODE)) {
            val username = data?.getStringExtra("username") ?: "无名氏"
            if (!TextUtils.isEmpty(username)) {
                loginBinding.inputItemUsername.editText.setText(username)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()

    }
}