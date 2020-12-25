package com.example.hi_library.restful.annotation

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

@Target(AnnotationTarget.FUNCTION)
@Retention(RetentionPolicy.RUNTIME)
annotation class GET(val value:String)