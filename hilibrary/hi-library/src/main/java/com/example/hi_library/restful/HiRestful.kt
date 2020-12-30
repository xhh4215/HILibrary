package com.example.hi_library.restful

import com.example.hi_library.restful.callback.HiCall
import com.example.hi_library.restful.interceptor.HiInterceptor
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap


open class HiRestful constructor(val baseUrl: String, val callFactory: HiCall.Factory) {
    private var interceptors: MutableList<HiInterceptor> = mutableListOf()
    private var methodService: ConcurrentHashMap<Method, MethodParser> = ConcurrentHashMap()
    private var scheduler: Scheduler
    fun addInterceptor(interceptor: HiInterceptor) {
        interceptors.add(interceptor)
    }

    init {
        scheduler = Scheduler(callFactory, interceptors)
    }

    /**
     * { proxy, method, args ->


    } as T
     */
    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service),
            object : InvocationHandler{
                override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any {
                    var methodParser = methodService[method]
                    if (methodParser == null) {
                        methodParser = MethodParser.parse(baseUrl, method)
                        methodService.put(method, methodParser)
                    }
                    var request = methodParser.newRequest(method,args)
                    //callFactory.newCall(request)
                   return  scheduler.newCall(request)
                }

            }
        )  as T
    }

}