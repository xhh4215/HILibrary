package com.example.asproj.http.api

import com.example.asproj.http.interceptor.BizInterceptor
import com.example.asproj.http.RetrofitCallFactory
import com.example.asproj.http.interceptor.HttpStatusInterceptor
import com.example.common.utils.SPUtil
import com.example.hi_library.restful.HiRestful

/***
 * @author 栾桂明
 * @description HIRestful网络框架的配置
 * @date 2020年 1月12日
 */
object ApiFactory {
    private const val KEY_DEGRADE_HTTP = "degrade_http"
    private const val HTTP_BASE_URL = "http://api.devio.org/as/"
    private const val HTTPS_BASE_URL = "https://api.devio.org/as/"
    private val degrade2http = SPUtil.getBoolean(KEY_DEGRADE_HTTP)

    //baseUrl
    private val baseUrl = if (degrade2http) HTTP_BASE_URL else HTTPS_BASE_URL
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
        SPUtil.putBoolean(KEY_DEGRADE_HTTP, false)
    }

    /***
     * 通过接口代理的方式创建发起网路请求的对象
     */
    fun <T> create(service: Class<T>): T {
        return hiRestful.create(service)
    }
}