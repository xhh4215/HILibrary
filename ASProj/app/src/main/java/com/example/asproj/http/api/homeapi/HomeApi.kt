package com.example.asproj.http.api.homeapi

import com.example.asproj.http.model.HomeModel
import com.example.asproj.http.model.TabCategory
import com.example.hi_library.restful.annotation.Field
import com.example.hi_library.restful.annotation.GET
import com.example.hi_library.restful.annotation.Path
import com.example.hi_library.restful.callback.HiCall


interface HomeApi {
    @GET("category/categories")
    fun queryTabList(): HiCall<List<TabCategory>>

    @GET("home/{categoryId}")
    fun queryTabCategoryList(
        @Path("categoryId") categoryId: String,
        @Field("pageIndex") pageIndex: Int,
        @Field("pageSize") pageSize: Int
    ): HiCall<HomeModel>
}