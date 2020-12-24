package com.example.asproj.rote

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor
import java.lang.RuntimeException

@Interceptor(name = "biz_interceptor", priority = 9)
class BizInterceptor : IInterceptor {
    private lateinit var context: Context

    override fun init(context: Context) {
        this.context = context
    }

    override fun process(postcard: Postcard, callback: InterceptorCallback) {
        val flag = postcard.extra
        when {
            (flag and (RouterFlag.FLAG_LOGIN) != 0) -> {
                //login
                callback.onInterrupt(RuntimeException("need login"))
                showToast("请先登录")
            }
            (flag and (RouterFlag.FLAG_AUTHENTICATION) != 0) -> {
                callback.onInterrupt(RuntimeException("need authentication"))
                showToast("需要实名认证")
            }
            (flag and (RouterFlag.FLAG_VIP) != 0) -> {
                callback.onInterrupt(RuntimeException("need vip"))
                showToast("需要会员")
            }
            else -> {
                callback.onContinue(postcard)
            }
        }
    }

    private fun showToast(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

}