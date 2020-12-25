package com.example.hi_library.restful.annotation
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
annotation class Path (val value:String)