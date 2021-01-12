package com.example.asproj.http.api.categoryapi

import com.example.asproj.http.model.Subcategory
import com.example.asproj.http.model.TabCategory
import com.example.hi_library.restful.annotation.GET
import com.example.hi_library.restful.annotation.Path
import com.example.hi_library.restful.callback.HiCall

/***
 * @author 栾桂明
 * @description 关于商品分类相关的API的定义
 * @date 2020年 1月12日
 */
interface CategoryApi {
    /***
     * 获取分类列表的接口
     */
    @GET("category/categories")
    fun queryCategoryList(): HiCall<List<TabCategory>>

    /***
     * 通过categoryId获取每个分类下的具体信息
     */
    @GET("category/subcategories/{categoryId}")
    fun querySubcategoryList(@Path("categoryId") categoryId: String): HiCall<List<Subcategory>>
}