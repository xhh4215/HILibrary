package com.example.hilibrary

import android.app.Application
import com.example.hilibrary.log.HiLogConfig
import com.example.hilibrary.log.HiLogManager

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        HiLogManager.init(object : HiLogConfig() {
            override fun getGlobalTag(): String {
                return "MyApplication"
            }

            override fun enable(): Boolean {
                return true;
            }
        })
    }
}