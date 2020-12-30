package com.example.asproj.http.api

import com.example.asproj.http.interceptor.BizInterceptor
import com.example.asproj.http.RetrofitCallFactory
import com.example.asproj.http.interceptor.HttpStatusInterceptor
import com.example.hi_library.restful.HiRestful

object ApiFactory {
    private const val baseUrl = "https://api.devio.org/as/"
    private val hiRestful = HiRestful(
        baseUrl,
        RetrofitCallFactory(baseUrl)
    )

    init {
        hiRestful.addInterceptor(BizInterceptor())
        hiRestful.addInterceptor(HttpStatusInterceptor())
    }

    fun <T> create(service: Class<T>): T {
        return hiRestful.create(service)
    }
}