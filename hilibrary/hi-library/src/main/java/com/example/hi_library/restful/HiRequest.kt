package com.example.hi_library.restful

import androidx.annotation.IntDef
import java.lang.IllegalStateException
import java.lang.reflect.Method
import java.lang.reflect.Type

open class HiRequest {


    //  请求方式类型
    @METHOD
    var httpMethod: Int = 0

    //存储headers数据
    var headers: MutableMap<String, String>? = null

    //存储参数数据
    var parameters: MutableMap<String, String>? = null

    //域名
    var domainUrl: String? = null

    //相对路径
    var relativeUrl: String? = null

    //返回类型
    var returnType: Type? = null

    var formPost: Boolean = true


    @IntDef(value = [METHOD.GET, METHOD.POST])
    annotation class METHOD {
        companion object {
            const val GET = 0
            const val POST = 1


        }
    }

    /***
     * 请求的完整url
     */
    fun endPointUrl(): String {
        if (relativeUrl == null) {
            throw IllegalStateException("relative url must not  be  null")
        }
        if (!relativeUrl!!.startsWith("/")) {
            return domainUrl + relativeUrl
        }

        val indexOf = domainUrl!!.indexOf("/")
        return domainUrl!!.substring(0, indexOf) + relativeUrl
    }

    fun addHeader(name: String, value: String) {
        if (headers == null) {
            headers = mutableMapOf()
        }
        headers!![name] = value

    }
}