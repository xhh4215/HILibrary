package com.example.hi_library.restful

import com.example.hi_library.restful.callback.HiCall
import com.example.hi_library.restful.interceptor.HiInterceptor
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


    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(
            service.classLoader,
            arrayOf<Class<*>>(service)
        ) { proxy, method, args ->
            var methodParser = methodService[method]
            if (methodParser == null) {
                methodParser = MethodParser.parse(baseUrl, method, args)
                methodService.put(method, methodParser)
            }
            var request = methodParser.newRequest()
            //callFactory.newCall(request)
            scheduler.newCall(request)

        } as T
    }

}