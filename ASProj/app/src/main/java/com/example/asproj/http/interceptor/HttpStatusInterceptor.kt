package com.example.asproj.http.interceptor

import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.example.asproj.rote.HiRoute
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.interceptor.HiInterceptor

/***
 * @author 栾桂明
 * @date 2020年 1月 12日
 * @description 根据response的code路由到相关界面
 * 简单的说就是登录的拦截
 */
class HttpStatusInterceptor : HiInterceptor {
    override fun intercept(chain: HiInterceptor.Chain): Boolean {
        val response = chain.response()
        if (!chain.isRequestPeriod && response != null) {
            when (response.code) {
                HiResponse.RC_NEED_LOGIN -> {
                    HiRoute.startActivity(null,destination = HiRoute.Destination.ACCOUNT_LOGIN)
                }
                HiResponse.RC_AUTH_TOKEN_EXPIRED, (HiResponse.RC_AUTH_TOKEN_INVALID), (HiResponse.RC_USER_FORBID) -> {
                    var helpUrl: String? = null
                    if (response.errorData != null) {
                        helpUrl = response.errorData!!["helpUr;"]
                    }
                    val bundle = Bundle()
                    bundle.putString("degrade_title","非法访问")
                    bundle.putString("degrade_desc",response.msg)
                    bundle.putString("degrade_action",helpUrl)
                    HiRoute.startActivity(null,bundle,HiRoute.Destination.DEGRADE_GLOBAL)


                }
            }
        }
        return false
    }

}