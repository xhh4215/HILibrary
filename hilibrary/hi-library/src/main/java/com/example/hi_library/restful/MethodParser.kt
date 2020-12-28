package com.example.hi_library.restful

import com.example.hi_library.restful.annotation.*
import com.example.hi_library.restful.callback.HiCall
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.IllegalStateException

class MethodParser(
    val baseUrl: String,
    method: Method,
    args: Array<Any>
) {
    private  var domainUrl: String?=null
    private var fromPost = true
    private var httpMethod: Int = 0
    private lateinit var relativeUrl: String
    private lateinit var returnType: Type
    private var headers: MutableMap<String, String> = mutableMapOf()
    private var parameters: MutableMap<String, String> = mutableMapOf()

    init {
        //解析方法的注解  such get  headers   post  baseurl
        parseMethodAnnotations(method)
        //解析方法参数   path  field
        parseMethodParameters(method, args)
        //解析方法的返回值
        parseMethodReturnType(method)
    }

    /***
     * interface Services{
     * @Headers("auth-token:token","accountId:123456")
     * @BaseUrl("https://api.devio.org/as/)
     * @POST("/cities/{province}")
     * @GET("/cities")
     * fun listCities(@PATH("province") province:Int,@Field("page") page:Int):HiCall<JsonObject>
     * }
     */
    private fun parseMethodReturnType(method: Method) {
        if (method.returnType != HiCall::class.java) {
            throw IllegalStateException(String.format("method %s must be type of HiCall.class${method.name}"))
        }
        //获取方法的范型返回参数
        val genericReturnType = method.genericReturnType
        // 判断范型返回参数是不是包含范型
        if (genericReturnType is ParameterizedType) {
            //获取范型返回参数的个数
            val actualTypeArguments = genericReturnType.actualTypeArguments
            require(actualTypeArguments.size == 1) {
                "method %s can only has one generic return type"
            }
            returnType = actualTypeArguments[0]
        } else {
            throw IllegalStateException(String.format("method %s must has one generic return type${method.name}"))
        }
    }

    private fun parseMethodParameters(method: Method, args: Array<Any>) {
        //此处得到的是一个二维数组 因为一个方法可以有多个参数
        val parameterAnnotations = method.parameterAnnotations
        val equals = method.parameterAnnotations.size == args.size
        require(equals) {
            String.format("arguments annotations count %s do not match expect count %s${parameterAnnotations.size},${args.size} ")
        }
        for (index in args.indices) {

            val annotations = parameterAnnotations[index]
            //判断每个参数是不是只包含一个注解
            require(annotations.size <= 1) {
                "field can only hash one annotation :index=$index"
            }
            val value = args[index]
            //不是基本数据类型 则 抛出异常
            require(isPrimitive(value)) {
                "8 basic types are supported for now :index=$index"
            }
            val annotation = annotations[0]
            when (annotation) {
                is Field -> {
                    val key = annotation.value
                    val value = args[index]
                    parameters[key] = value.toString()
                }
                is Path -> {
                    //要不替换的相对路径中的String
                    val replaceName = annotation.value
                    val replacement = value.toString()
                    val newRelativeUrl = relativeUrl.replace("{$replaceName}", replacement)
                    relativeUrl = newRelativeUrl
                }
                else -> {
                    throw  IllegalStateException("can not handle parameter annotation${annotation.javaClass.toString()} ")

                }
            }
        }
    }

    /***
     * 判断参数类型是不是八种基本数据类型
     */
    private fun isPrimitive(value: Any): Boolean {
        if (value.javaClass == String::class.java) {
            return true
        }

        try {
            //获取一个装箱属性类型
            val field = value.javaClass.getField("TYPE")
            //获取装箱属性的真实类型
            val clazz = field[null] as Class<*>
            //判断是不是基本数据类型
            if (clazz.isPrimitive) {
                return true
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()

        } catch (e: NoSuchFieldException) {
            e.printStackTrace()

        }
        return false
    }

    /***
     * 方法的注解解析
     */
    private fun parseMethodAnnotations(method: Method) {
        //获取方法上标注的注解
        val annotations = method.annotations
        for (annotation in annotations) {
            when (annotation) {
                is GET -> {
                    relativeUrl = annotation.value
                    httpMethod = HiRequest.METHOD.GET

                }
                is POST -> {
                    relativeUrl = annotation.value
                    httpMethod = HiRequest.METHOD.POST
                    fromPost = annotation.fromPost

                }
                is Headers -> {
                    val headersArray = annotation.value
                    for (header in headersArray) {
                        // 获取： 的位置
                        val colon = header.indexOf(":")
                        check(!(colon == 0 || colon == -1)) {
                            String.format("Headers value must be in from [name:value],but found[%s]$header ")
                        }
                        val name = header.substring(0, colon)
                        val value = header.substring(colon + 1).trim()
                        headers[name] = value

                    }
                }
                is BaseUrl -> {
                    domainUrl = annotation.value

                }
                else -> {
                    throw IllegalStateException(String.format("cannot handler method annotation${annotation.javaClass.toString()}"))
                }
            }


        }
        require((httpMethod == HiRequest.METHOD.GET) ||(httpMethod == HiRequest.METHOD.POST)) {
            String.format("method  %s must has one of GET ,POST${method.name}")

        }
        if (domainUrl == null) {
            domainUrl = baseUrl
        }
    }

    fun newRequest(): HiRequest {
        var request = HiRequest()
        request.domainUrl = domainUrl
        request.headers = headers
        request.httpMethod = httpMethod
        request.parameters = parameters
        request.returnType = returnType
        request.relativeUrl = relativeUrl
        request.formPost = fromPost
        return request
    }

    companion object {
        fun parse(baseUrl: String, method: Method, args: Array<Any>): MethodParser {
            return MethodParser(baseUrl, method, args)
        }
    }
}