package com.example.asproj.http.api.categoryapi

import com.example.asproj.http.model.Subcategory
import com.example.asproj.http.model.TabCategory
import com.example.hi_library.restful.annotation.GET
import com.example.hi_library.restful.annotation.Path
import com.example.hi_library.restful.callback.HiCall

interface CategoryApi {
    /***
     * 获取分类列表的接口
     */
    @GET("category/categories")
    fun queryCategoryList(): HiCall<List<TabCategory>>

    @GET("category/subcategories/{categoryId}")
    fun querySubcategoryList(@Path("categoryId") categoryId: String):HiCall<List<Subcategory>>
}