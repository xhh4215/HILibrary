package com.example.asproj.http

import com.example.hi_library.restful.annotation.Field
import com.example.hi_library.restful.annotation.GET
import com.example.hi_library.restful.callback.HiCall
import com.google.gson.JsonObject


interface  TestApi{
   @GET("cities")
   fun listCities(@Field("name") name:String):HiCall<JsonObject>
}