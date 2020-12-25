package com.example.hi_library.restful

import com.example.hi_library.restful.callback.HiCall
import com.example.hi_library.restful.callback.HiCallBack
import com.example.hi_library.restful.interceptor.HiInterceptor

/***
 * 代理CallFactory创建出来的Call对象，从而实现拦截器的派发动作
 */
class Scheduler(
    val callFactory: HiCall.Factory,
    val interceptors: MutableList<HiInterceptor>
) {
    fun newCall(request: HiRequest): HiCall<*> {
        val newCall = callFactory.newCall(request)
        return ProxyCall(newCall, request)
    }


    internal inner class ProxyCall<T>(
        val delegate: HiCall<T>,
        val request: HiRequest
    ) : HiCall<T> {
        override fun execute(): HiResponse<T> {
            dispatchInterceptor(request, null)
            val response = delegate.execute()
            dispatchInterceptor(request,response)
            return response
        }


        override fun enqueue(callBack: HiCallBack<T>) {
            dispatchInterceptor(request,null)
            delegate.enqueue(object :HiCallBack<T>{
                override fun onSuccess(response: HiResponse<T>) {
                    callBack?.onSuccess(response)
                 }

                override fun onFailed(throwable: Throwable) {
                    callBack?.onFailed(throwable)
                 }

            })
        }

        private fun dispatchInterceptor(request: HiRequest, response: HiResponse<T>?) {
            InterceptorChain(request, response).dispatch()

        }


        internal inner class InterceptorChain(
            val request: HiRequest,
            val response: HiResponse<T>?
        ) : HiInterceptor.Chain {
            //表示分发的第几个拦截器
            var callIndex = 0
            override val isRequestPeriod: Boolean
                get() = response == null

            override fun request(): HiRequest {
                return request
            }

            override fun response(): HiResponse<*>? {
                return response
            }


            fun dispatch() {
                val interceptor = interceptors[callIndex]
               val intercept =  interceptor.intercept(this)
                callIndex++
                if (!intercept&&callIndex<interceptors.size){
                    dispatch()
                }
            }

        }

    }
}