package com.example.asproj.rote

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.launcher.ARouter

/***
 * 全局降级服务，当路由的时候  目标也不存在重定向到统一错误页
 */
@Route(path = "/degrade/global/service")
class DegradeServiceImp : DegradeService {
    /***
     * 当路由失败的时候回调的方法
     */
    override fun onLost(context: Context?, postcard: Postcard?) {
         ARouter.getInstance().build("/degrade/global/activity").greenChannel().navigation()
    }

    /***
     * 初始化的方法
     */
    override fun init(context: Context?) {

    }

}