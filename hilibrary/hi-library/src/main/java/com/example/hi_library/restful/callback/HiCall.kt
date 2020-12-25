package com.example.hi_library.restful.callback

import com.example.hi_library.restful.HiRequest
import com.example.hi_library.restful.HiResponse
import java.io.IOException

/***
 *
 */
interface HiCall<T> {
    @Throws(IOException::class)
    fun execute(): HiResponse<T>

    fun enqueue(callBack: HiCallBack<T>)

    interface  Factory{
        fun  newCall(request: HiRequest):HiCall<*>
    }
}