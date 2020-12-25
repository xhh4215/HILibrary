package com.example.hi_library.restful.annotation

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class POST(val value: String, val fromPost: Boolean = true)