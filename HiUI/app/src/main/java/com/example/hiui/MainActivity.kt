package com.example.hiui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.hi_ui.ui.tab.bottom.HiTabBottom
import com.example.hi_ui.ui.tab.bottom.HiTabBottomInfo
import com.example.hiui.tab.HiTabBottomActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.tv_hilog)
        textView.setOnClickListener {
            val intent = Intent(this@MainActivity, HiTabBottomActivity::class.java)
            startActivity(intent)
        }
    }
}