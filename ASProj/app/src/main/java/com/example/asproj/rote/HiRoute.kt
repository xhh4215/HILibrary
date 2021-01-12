package com.example.asproj.rote

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.example.hi_library.utils.AppGlobals

object HiRoute {
    /***
     * 界面跳转的枚举变量的定义
     */
    enum class Destination(val path: String) {
        GOODS_LIST("/goods/list"),
        ACCOUNT_REGISTER("/account/registration"),
        ACCOUNT_LOGIN("/account/login")
    }

    /***
     * 拉起游览器
     */
    fun startActivity4Browser(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        AppGlobals.get()?.startActivity(intent)
    }


    /***
     * 使用Aroute实现界面的跳转 携带参数
     */
    fun startActivity(
        context: Context,
        bundle: Bundle,
        destination: Destination,
        requestCode: Int = -1
    ) {
        val postCard = ARouter.getInstance().build(destination.path).with(bundle)
        if (requestCode == -1 || context !is Activity) {
            postCard.navigation(context)
        } else {
            postCard.navigation(context, requestCode)
        }

    }


    fun startActivity(
        context: Context,
        destination: Destination,
        requestCode: Int = -1
    ) {
        val postCard = ARouter.getInstance().build(destination.path)
        if (requestCode == -1 || context !is Activity) {
            postCard.navigation(context)
        } else {
            postCard.navigation(context, requestCode)
        }
    }


    /****
     * 使用Aroute实现参数的自动注入
     */
    fun inject(target: Any) {
        ARouter.getInstance().inject(target)
    }
}