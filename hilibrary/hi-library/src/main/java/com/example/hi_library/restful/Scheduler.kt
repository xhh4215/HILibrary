package com.example.hi_library.restful

import android.util.Log
import android.widget.Toast
import com.example.hi_library.cache.HiStorage
import com.example.hi_library.executor.HiExecutor
import com.example.hi_library.restful.annotation.CacheStrategy
import com.example.hi_library.restful.callback.HiCall
import com.example.hi_library.restful.callback.HiCallBack
import com.example.hi_library.restful.interceptor.HiInterceptor
import com.example.hi_library.utils.MainHandler
import kotlinx.coroutines.MainScope
import java.time.chrono.HijrahEra
import java.util.*

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
            if (request.cacheStrategy == CacheStrategy.CACHE_FIRST) {
                val cacheResponse = readCache<T>()
                if (cacheResponse.data != null) {
                    return cacheResponse
                }
            }
            val response = delegate.execute()
            saveCacheIfNeed(response)
            dispatchInterceptor(request, response)
            return response
        }


        override fun enqueue(callBack: HiCallBack<T>) {
            dispatchInterceptor(request, null)
            if (request.cacheStrategy == CacheStrategy.CACHE_FIRST) {
                HiExecutor.execute(runnable = Runnable {
                    val cacheResponse = readCache<T>()
                    if (cacheResponse.data != null) {
                        MainHandler.sendAtFontOfQueue(runnable = Runnable {
                            Log.d("tag","加载缓存数据")
                            callBack.onSuccess(cacheResponse)
                        })
                    }
                })
            }
            delegate.enqueue(object : HiCallBack<T> {
                override fun onSuccess(response: HiResponse<T>) {
                    dispatchInterceptor(request, response)
                    callBack?.onSuccess(response)
                    saveCacheIfNeed(response)
                }

                override fun onFailed(throwable: Throwable) {
                    callBack?.onFailed(throwable)
                }

            })

        }

        private fun saveCacheIfNeed(response: HiResponse<T>) {
            if (request.cacheStrategy == CacheStrategy.CACHE_FIRST || request.cacheStrategy == CacheStrategy.NET_CACHE) {
                //更新缓存
                if (response.data != null) {
                    HiExecutor.execute(runnable = Runnable {
                        HiStorage.saveCache(request.getCacheKey(), response.data)
                    })
                }
            }
        }

        private fun <T> readCache(): HiResponse<T> {
            val cacheKey = request.getCacheKey()
            val cache = HiStorage.getCache<T>(cacheKey)
            val cacheResponse = HiResponse<T>()
            cacheResponse.data = cache
            cacheResponse.code = HiResponse.CACHE_SUCCESS
            cacheResponse.msg = "缓存获取成功"
            return cacheResponse
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
                val intercept = interceptor.intercept(this)
                callIndex++
                if (!intercept && callIndex < interceptors.size) {
                    dispatch()
                }
            }

        }

    }


}