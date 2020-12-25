package com.example.hi_library.restful.interceptor

import com.example.hi_library.restful.HiRequest
import com.example.hi_library.restful.HiResponse

interface HiInterceptor {
     fun intercept(chain: Chain): Boolean

    /***
     * Chain 对象在我们派发拦截器的时候创建
     */
    interface Chain {

        val isRequestPeriod: Boolean get() = false
        fun request(): HiRequest

        /***
         * response在网络发起之前是空的
         */
        fun response(): HiResponse<*>?
    }
}