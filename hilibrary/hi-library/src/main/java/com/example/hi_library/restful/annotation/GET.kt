package com.example.hi_library.restful.annotation

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

/***
 * @author 栾桂明
 * @date 2020年12月30日
 * @desc 标识GET请求的注解
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(RetentionPolicy.RUNTIME)
annotation class GET(val value:String)