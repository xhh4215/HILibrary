package com.example.hi_library.restful

import androidx.annotation.IntDef
import java.lang.reflect.Method
import java.lang.reflect.Type

open class HiRequest {
    //  请求方式类型
    @METHOD
    var httpMethod: Int = 0

    //存储headers数据
    var headers: MutableMap<String, String>? = null

    //存储参数数据
    var parameters: MutableMap<String, Any>? = null

    //域名
    var domainUrl: String? = null

    //相对路径
    var relativeUrl: String? = null

    //返回类型
    var returnType: Type? = null


    @IntDef(value = [METHOD.GET,METHOD.POST])
    internal annotation class METHOD {
        companion object {
            const val GET = 0
            const val POST = 1


        }
    }
}