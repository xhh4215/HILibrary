package com.example.common.unuse.singledata.kotlin

/***
 * 静态内部类的模式实现单例
 * 1 线程安全
 * 2 实例的唯一性
 * 3 延迟的实例化 不会占用空间
 */
class SingleInstanceFInKotlin {
    companion object {
        val instance = SingletonProvider.holder

    }

    private object SingletonProvider {
        val holder = SingleInstanceFInKotlin()

    }
}