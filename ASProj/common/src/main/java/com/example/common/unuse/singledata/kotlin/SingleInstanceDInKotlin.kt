package com.example.common.unuse.singledata.kotlin

/***
 * kotlin中的双重校验锁
 */
class SingleInstanceDInKotlin private constructor() {
    companion object {
        val INSTANCE: SingleInstanceDInKotlin by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            SingleInstanceDInKotlin()
        }
    }

    fun getInstacne(name: String): String {
        return name
    }


}