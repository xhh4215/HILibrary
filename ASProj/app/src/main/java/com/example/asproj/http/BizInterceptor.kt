package com.example.asproj.http

import com.example.common.utils.SPUtil
import com.example.hi_library.log.common.HiLog
import com.example.hi_library.restful.interceptor.HiInterceptor

class BizInterceptor : HiInterceptor {
    override fun intercept(chain: HiInterceptor.Chain): Boolean {
        if (chain.isRequestPeriod) {
            val request = chain.request()
            val boardingPass = SPUtil.getString("boarding-pass")?:""
            request.addHeader("auth-token", "MTU5Mjg1MDg3NDcwNw11.26==")
            request.addHeader("boarding-pass", boardingPass)
        } else if (chain.response() != null) {
             HiLog.d("BizInterceptor",chain.request().endPointUrl())
             HiLog.d("BizInterceptor",chain.response()!!.rawData)
        }
        return false
    }

}