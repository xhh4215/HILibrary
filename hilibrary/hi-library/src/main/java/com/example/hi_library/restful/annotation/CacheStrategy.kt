package com.example.hi_library.restful.annotation

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.VALUE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
annotation class CacheStrategy(
    val value: Int = NET_ONLY
) {
    companion object {
        //  请求接口的时候先读取本地的缓存，在读取接口的数据 接口成功会更新缓存（页面初始化）
        const val CACHE_FIRST = 0

        // 仅仅用于接口的请求（分页 或 独立页面）
        const val NET_ONLY = 1

        //  先接口接口成功后更新缓存（下拉刷新）
        const val NET_CACHE = 2
    }
}