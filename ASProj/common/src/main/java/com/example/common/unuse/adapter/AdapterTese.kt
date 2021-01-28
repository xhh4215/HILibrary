package com.example.common.unuse.adapter

fun main() {
    //类适配器模式
    val adapterClass = AdapterClass()
    adapterClass.apply {
        request1()
        request2()
    }
}