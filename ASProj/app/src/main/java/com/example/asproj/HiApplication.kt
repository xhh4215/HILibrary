package com.example.asproj

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.example.common.manager.ActivityManager
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta

/***
 * @author  栾桂明
 * @date 2020年12月21日
 * @desc 项目的application 进行项目的初始化的工作
 */
class HiApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ActivityManager.instance.init(this)
        initArouter()
        initBugly()

    }

    @Override
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // tinnker的初始化
        MultiDex.install(base)
        Beta.installTinker()
    }

    /***
     * 初始化热修复框架
     */
    private fun initBugly() {
        Bugly.init(this, "10d86971eb", true)
        Bugly.setIsDevelopmentDevice(this, true)
    }

    /***
     * 初始化阿里巴巴的路由导航框架
     */
    private fun initArouter() {
        // 阿里巴巴的路由导航框架的初始化
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}