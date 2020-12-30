package com.example.hi_library.restful.annotation

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

/***
 * @author 栾桂明
 * @date 2020年12月30日
 * @desc 为请求添加参数的注解
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
annotation class Field(val value:String)