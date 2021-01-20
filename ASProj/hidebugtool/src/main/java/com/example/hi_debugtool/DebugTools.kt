package com.example.hi_debugtool

import android.content.Intent
import android.os.Process
import com.example.common.utils.SPUtil
import com.example.hi_library.utils.AppGlobals

/****
 * @author 栾桂明
 * @description 项目使用的调试使用的工具方法
 * @date  2020年1月 12日
 */
class DebugTools {
    /***
     * 获取项目的构建版本信息
     */
    fun buildVersion() = "构建版本:${BuildConfig.VERSION_CODE}.${BuildConfig.VERSION_NAME}"

    /***
     * 获取项目的构建时间
     */
    fun buildTime() = "构建时间{${BuildConfig.BUILD_TIME}}"

    /***
     * 获取项目的构建环境
     */
    fun buildEnvironment() = if (BuildConfig.DEBUG) "测试环境" else "正式环境"

    /***
     * Https 降级为Http
     */
    @HiDebug("一键开启Https的降级", "开启降级之后就可以使用抓包工具，明文显示")
    fun degrade2Http() {
        SPUtil.putBoolean("degrade_http", true)
        val context = AppGlobals.get()?.applicationContext ?: return
        //获取启动当前程序的intent对象
        val intent = context.packageManager.getLaunchIntentForPackage(context.packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        //启动Activity
        context.startActivity(intent)
        //杀死当前进程 并主动进入启动页 完成重启
        Process.killProcess(Process.myPid())
    }
}