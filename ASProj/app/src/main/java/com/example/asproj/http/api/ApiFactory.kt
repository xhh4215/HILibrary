package com.example.asproj.http.api

import com.example.asproj.http.interceptor.BizInterceptor
import com.example.asproj.http.RetrofitCallFactory
import com.example.asproj.http.interceptor.HttpStatusInterceptor
import com.example.hi_library.restful.HiRestful

/***
 * @author 栾桂明
 * @description HIRestful网络框架的配置
 * @date 2020年 1月12日
 */
object ApiFactory {
    //baseUrl
    private const val baseUrl = "https://api.devio.org/as/"
    private val hiRestful = HiRestful(
        baseUrl,
        RetrofitCallFactory(baseUrl)
    )

    /***
     * 添加拦截器
     */
    init {
        hiRestful.addInterceptor(BizInterceptor())
        hiRestful.addInterceptor(HttpStatusInterceptor())
    }

    /***
     * 通过接口代理的方式创建发起网路请求的对象
     */
    fun <T> create(service: Class<T>): T {
        return hiRestful.create(service)
    }
}