package com.example.asproj

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.manager.ActivityManager

/***
 * @author  栾桂明
 * @date 2020年12月21日
 * @desc 项目的application 进行项目的初始化的工作
 */
class HiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ActivityManager.instance.init(this)
        // 阿里巴巴的路由导航框架的初始化
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}