package com.example.asproj

import android.app.Application
import com.example.common.manager.ActivityManager
import com.example.common.uicomponent.HiBaseApplication

class HiApplication:HiBaseApplication(){
    override fun onCreate() {
        super.onCreate()
        ActivityManager.instance.init(this)
    }
}