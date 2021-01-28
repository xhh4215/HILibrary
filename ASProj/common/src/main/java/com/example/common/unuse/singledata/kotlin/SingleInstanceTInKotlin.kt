package com.example.common.unuse.singledata.kotlin

/***
 * kotlin 的懒汉模式实现单例
 * 1 在方法被调用的时候创建的 是使用时间换空间
 * 2 多线程并发可能会创建多个
 */
class SingleInstanceTInKotlin private constructor() {
    companion object {
        private var instance: SingleInstanceTInKotlin? = null
            get() {
                if (field == null) {
                    field = SingleInstanceTInKotlin()
                }
                return field
            }
        @Synchronized
        fun getSingle(): SingleInstanceTInKotlin {
            return instance!!
        }
    }
}