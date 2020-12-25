package com.example.hi_library.restful.callback

import com.example.hi_library.restful.HiResponse

/***
 * @desc 响应报文
 */
interface HiCallBack<T> {
    /***
     * 网络请求成功的回调
     */
    fun onSuccess(response: HiResponse<T>)

    /***
     * 网络请求失败的回调
     */
    fun onFailed(throwable: Throwable)
}