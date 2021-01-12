package com.example.asproj.http.api.goodsapi

import com.example.asproj.http.model.GoodsList
import com.example.hi_library.restful.annotation.Field
import com.example.hi_library.restful.annotation.GET
import com.example.hi_library.restful.annotation.Path
import com.example.hi_library.restful.callback.HiCall
/***
 * @author 栾桂明
 * @description 关于商品相关的API的定义
 * @date 2020年 1月12日
 */
interface GoodsApi {
    /***
     * 根据categoryId查找详细的商品信息
     */
    @GET("goods/goods/{categoryId}")
    fun queryCategoryGoodsList(
        @Path("categoryId") categoryId: String,
        @Field("subcategoryId") subcategoryId: String,
        @Field("pageSize") pageSize: Int,
        @Field("pageIndex") pageIndex: Int
    ): HiCall<GoodsList>
}