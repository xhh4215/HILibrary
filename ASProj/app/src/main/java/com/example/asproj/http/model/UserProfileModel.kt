package com.example.asproj.http.model

data class CourseNotice(val total:Int, val list:List<Notice>?)

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

/***
 * 个人中心页返回的数据json格式
 */
data class UserProfile(
    val isLogin: Boolean,
    val favoriteCount: Int,
    val browseCount: Int,
    val learnMinutes: Int,
    val userName: String,
    var userIcon: String,
    val bannerNoticeList: List<Notice>
)