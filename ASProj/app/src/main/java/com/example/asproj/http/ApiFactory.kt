package com.example.asproj.http

import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.HiRestful

object ApiFactory {
    private const val baseUrl = "https://api.devio.org/as/"
    private val hiRestful = HiRestful(baseUrl, RetrofitCallFactory(baseUrl))

    init {
        hiRestful.addInterceptor(BizInterceptor())
    }

    fun <T> create(service: Class<T>): T {
        return hiRestful.create(service)
    }
}