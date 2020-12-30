package com.example.asproj.http.model

/***
 * 个人中心的banner图列表使用的信息
 */
data class Notice(
    val id: String,
    val sticky: Int,
    val type: String,
    val title: String,
    val subtitle: String,
    val url: String,
    val cover: String,
    val createTime: String
)