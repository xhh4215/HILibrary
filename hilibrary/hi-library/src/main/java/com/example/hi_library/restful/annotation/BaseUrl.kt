package com.example.hi_library.restful.annotation

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

/***
 * @author 栾桂明
 * @date 2020年12月30
 * @desc 一个标识baseUrl的注解
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(RetentionPolicy.RUNTIME)
annotation class BaseUrl(val value: String)