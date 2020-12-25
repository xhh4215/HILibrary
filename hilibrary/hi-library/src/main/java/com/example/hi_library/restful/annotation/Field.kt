package com.example.hi_library.restful.annotation

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
annotation class Field(val value:String)