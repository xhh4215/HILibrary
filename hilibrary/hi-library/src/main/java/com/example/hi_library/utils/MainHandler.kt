package com.example.hi_library.utils

import android.os.Handler
import android.os.Looper
import android.os.Message

object MainHandler {
    private val handler = Handler(Looper.getMainLooper())


    fun post(runnable: Runnable) {
        handler.post { runnable }
    }

    fun postDelay(runnable: Runnable, delay: Long) {
        handler.postDelayed(runnable, delay)
    }

    fun sendAtFontOfQueue(runnable: Runnable) {
        val message = Message.obtain(handler, runnable)
        //发送的消息插入到消息队列的最前边
        handler.sendMessageAtFrontOfQueue(message)
    }
}