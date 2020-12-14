package com.example.hilibrary

import android.app.Application
import com.example.hilibrary.log.HiLogConfig
import com.example.hilibrary.log.HiLogManager
import com.example.hilibrary.log.printer.HiConsolePrinter
import com.google.gson.Gson

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        HiLogManager.init(object : HiLogConfig() {
            override fun injectJsonParser(): JsonParser {
                return JsonParser { Gson().toJson(it) }
            }

            override fun getGlobalTag(): String {
                return "MyApplication"
            }

            override fun enable(): Boolean {
                return true;
            }
        },HiConsolePrinter())
    }
}