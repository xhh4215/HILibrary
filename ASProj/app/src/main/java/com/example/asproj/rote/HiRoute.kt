package com.example.asproj.rote

import android.content.Intent
import android.net.Uri
import com.example.hi_library.log.utils.AppGlobals

object HiRoute {
    fun startActivity4Browser(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        AppGlobals.get()?.startActivity(intent)
    }
}