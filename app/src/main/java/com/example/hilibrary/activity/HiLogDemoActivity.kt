package com.example.hilibrary.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.hilibrary.R
import com.example.hilibrary.databinding.ActivityHiLogBinding
import com.example.hilibrary.log.HiLog
import com.example.hilibrary.log.HiLogConfig
import com.example.hilibrary.log.HiLogType

class HiLogDemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHiLogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hi_log)
        binding.printMessage.setOnClickListener {
            printLog()
        }
    }

    private fun printLog() {
        HiLog.log(object : HiLogConfig() {
            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth() = 0
        }, HiLogType.E, "-------", "5566")
        HiLog.a("0099")
    }

}