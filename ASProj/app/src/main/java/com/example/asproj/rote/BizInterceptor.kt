package com.example.asproj.rote

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import com.alibaba.android.arouter.launcher.ARouter
import com.example.asproj.biz.account.AccountManager
import com.example.hi_library.utils.MainHandler
import java.lang.RuntimeException

@Interceptor(name = "biz_interceptor", priority = 9)
class BizInterceptor : IInterceptor {
    private lateinit var context: Context

    override fun init(context: Context) {
        this.context = context
    }

    override fun process(postcard: Postcard?, callback: InterceptorCallback) {
        val flag = postcard!!.extra
        when {
            (flag and (RouterFlag.FLAG_LOGIN) != 0) -> {
                //login
                callback.onInterrupt(RuntimeException("need login"))
                loginInterceptor(postcard, callback)
            }
            else -> {
                callback.onContinue(postcard)
            }
        }
    }

    private fun loginInterceptor(postcard: Postcard?, callback: InterceptorCallback) {
        MainHandler.post(runnable = Runnable {
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
            if (AccountManager.isLogin()) {
                callback?.onContinue(postcard)
            } else {
                AccountManager.login(context, Observer { success ->
                    callback?.onContinue(postcard)
                })
            }
        })
    }

}