package com.example.common.manager

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.lang.ref.WeakReference

class ActivityManager private constructor() {
    //使用弱引用防止内存泄漏
    private val activityRefs = ArrayList<WeakReference<Activity>>()

    //应用前后台切换的监听
    private val frontbackCallback = ArrayList<FrontBackCallback>()

    //用来统计有多少个activity处于前台
    private var activityStartCount = 0

    //标识是否处于前台
    private var front = true
    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(InnerActivityLifecycleCallback())
    }


    inner class InnerActivityLifecycleCallback : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStarted(activity: Activity) {
            activityStartCount++
            //activityStartCount>0 说明应用处于可见的状态 也就是前台
            // !front 之前是不是在后台
            if (!front && activityStartCount > 0) {
                front = true
                onFrontBackChanged(front)
            }
        }

        private fun onFrontBackChanged(front: Boolean) {
            for (frontBackCallback in frontbackCallback) {
                frontBackCallback.onChanged(front)
            }
        }

        val topActivity:Activity? get(){
            if (activityRefs.size<=0){
                return  null
            }else{
                activityRefs[activityRefs.size-1].get()
            }
            return null
        }

        override fun onActivityDestroyed(activity: Activity) {
            for (activityRef in activityRefs) {
                if (activityRef != null && activityRef.get() == activity) {
                    activityRefs.remove(activityRef)
                    break
                }
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        }

        override fun onActivityStopped(activity: Activity) {
            activityStartCount--
            if (activityStartCount <= 0 && front) {
                front = false
                onFrontBackChanged(front)
            }
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityRefs.add(WeakReference(activity))
        }

        override fun onActivityResumed(activity: Activity) {
        }

    }

    /***
     * 添加应用前后台切换的监听
     */
    fun addFrontBackCallback(callback: FrontBackCallback) {
        frontbackCallback.add(callback)
    }

    /***
     * 移除应用前后台切换的监听
     */
    fun removeFrontBackCallback(callback: FrontBackCallback) {
        frontbackCallback.remove(callback)
    }

    interface FrontBackCallback {
        fun onChanged(front: Boolean)
    }

    companion object {
        val instance: ActivityManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityManager()
        }
    }
}