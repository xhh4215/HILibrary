package com.example.common.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.hi_library.utils.AppGlobals

/***
 * 一个SharePreference工具类
 */
object SPUtil {
    private const val CACHE_FILE = "cache_file"
    fun putString( key: String, value: String) {
        val shared = getShared()
        shared?.edit()?.putString(key, value)?.commit()

    }

    fun getString(key: String): String? {
        val shared = getShared()
        return shared?.getString(key, null)
    }

    fun putBoolean( key: String, value: Boolean) {
        val shared = getShared()
        shared?.edit()?.putBoolean(key, value)?.commit()

    }

    fun getBoolean(key: String): Boolean {
        val shared = getShared()
        return shared?.getBoolean(key, false)?:false
    }

    fun putInt( key: String, value: Int) {
        val shared = getShared()
        shared?.edit()?.putInt(key, value)?.commit()

    }


    fun getInt(key: String): Int? {
        val shared = getShared()
        return shared?.getInt(key, 0)
    }


    fun getShared(): SharedPreferences? {
        val application: Application? = AppGlobals.get()
        if (application != null) {
            return application.getSharedPreferences(CACHE_FILE, Context.MODE_PRIVATE)

        }
        return null

    }
}