package com.example.asproj.http.api.accountapi

import com.example.asproj.http.model.CourseNotice
import com.example.asproj.http.model.UserProfile
import com.example.hi_library.restful.annotation.Field
import com.example.hi_library.restful.annotation.GET
import com.example.hi_library.restful.annotation.POST
import com.example.hi_library.restful.callback.HiCall

interface AccountApi {
    /***
     * 登录
     */
    @POST("user/login")
    fun login(
        @Field("userName") userName: String,
        @Field("password") password: String
    ): HiCall<String>

    /***
     * 注册
     */
    @POST("user/registration")
    fun register(
        @Field("userName") userName: String,
        @Field("password") password: String,
        @Field("imoocId") imoocId: String,
        @Field("orderId") orderId: String
    ): HiCall<String>

    /***
     * 获取用户信息
     */
    @GET("user/profile")
    fun profile(): HiCall<UserProfile>

    /***
     * 通知
     */
    @GET("notice")
    fun notice():HiCall<CourseNotice>

}