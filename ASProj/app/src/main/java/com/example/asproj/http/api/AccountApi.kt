package com.example.asproj.http.api

import com.example.hi_library.restful.annotation.Field
import com.example.hi_library.restful.annotation.POST
import com.example.hi_library.restful.callback.HiCall

interface AccountApi {

    @POST("user/login")
    fun login(
        @Field("userName") userName: String,
        @Field("password") password: String
    ): HiCall<String>

    @POST("user/registration")
    fun register(
        @Field("userName") userName: String,
        @Field("password") password: String,
        @Field("imoocId") imoocId: String,
        @Field("orderId") orderId: String
    ): HiCall<String>
}