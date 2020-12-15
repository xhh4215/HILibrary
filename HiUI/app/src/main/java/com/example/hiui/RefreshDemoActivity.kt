package com.example.hiui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.hi_ui.ui.refresh.HiRefresh
import com.example.hi_ui.ui.refresh.HiRefreshLayout
import com.example.hi_ui.ui.refresh.HiTextOverView

class RefreshDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh_demo)
        val refreshLayout = findViewById<HiRefreshLayout>(R.id.refresh_layout)
        val overView = HiTextOverView(this)
        refreshLayout.setRefreshOverView(overView)
        refreshLayout.setRefreshListener(object : HiRefresh.HiRefreshListener {
            override fun enableRefresh(): Boolean {
                   return true;
            }

            override fun onRefresh() {
                Handler().postDelayed({refreshLayout.refreshFinished()},1000)
             }

        })
    }
}