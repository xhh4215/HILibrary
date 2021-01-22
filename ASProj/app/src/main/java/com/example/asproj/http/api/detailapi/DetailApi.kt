package com.example.asproj.http.api.detailapi

import com.example.asproj.http.model.DetailModel
import com.example.hi_library.restful.annotation.GET
import com.example.hi_library.restful.annotation.Path
import com.example.hi_library.restful.callback.HiCall

/****
 * @author 栾桂明
 * @date 2020年1月22日
 * @desc 获取商品详细信息相关的api
 */
interface DetailApi {
    /***
     * 获取商品的详细信息
     */
    @GET("goods/detail/{id}")
    fun queryDetail(@Path("id") goodId: String): HiCall<DetailModel>
}