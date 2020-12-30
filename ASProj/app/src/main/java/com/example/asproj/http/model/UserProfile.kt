package com.example.asproj.http.model

/***
 * 个人中心页返回的数据json格式
 */
data class UserProfile(
    val isLogin: Boolean,
    val favoriteCount: Int,
    val browseCount: Int,
    val learnMinutes: Int,
    val userName: String,
    val userIcon: String,
    val bannerNoticeList: List<Notice>
)