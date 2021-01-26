package com.example.asproj.http.api.detailapi

import com.example.asproj.http.model.Favorite
import com.example.hi_library.restful.annotation.POST
import com.example.hi_library.restful.annotation.Path
import com.example.hi_library.restful.callback.HiCall
import com.example.hi_library.restful.callback.HiCallBack


interface FactoryApi{
    @POST("favorites/{goodsId}")
    fun favorite(@Path("goodsId") goodsId:String): HiCall<Favorite>
}