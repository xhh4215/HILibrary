package com.example.hi_debugtool

/***
 * @author 栾桂明
 * @date 2020年 1月 12日
 * @description Https降级到Http的时候使用的注解
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class HiDebug(val name: String, val desc: String)