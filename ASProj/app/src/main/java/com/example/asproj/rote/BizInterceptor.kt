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
import com.alibaba.android.arouter.launcher.ARouter
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
                loginInterceptor()
            }
            else -> {
                callback.onContinue(postcard)
            }
        }
    }

    private fun loginInterceptor() {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show()
            ARouter.getInstance().build("/account/login").navigation()
        }
    }

}