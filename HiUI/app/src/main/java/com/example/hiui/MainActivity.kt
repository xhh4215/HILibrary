package com.example.hiui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.hi_ui.ui.tab.common.IHiTabLayout
import com.example.hi_ui.ui.tab.top.HiTabTopInfo
import com.example.hi_ui.ui.tab.top.HiTabTopLayout

class MainActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toptab = findViewById<Button>(R.id.topTab)
        toptab.setOnClickListener(this)
        val refresh = findViewById<Button>(R.id.refresh)
        refresh.setOnClickListener(this)
        val banner = findViewById<Button>(R.id.banner)
        banner.setOnClickListener(this)


    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.refresh -> {
                startActivity(Intent(this@MainActivity, RefreshDemoActivity::class.java))
            }
            R.id.topTab -> {
                startActivity(Intent(this@MainActivity, TabTopDemoActivity::class.java))
            }
            R.id.banner->{
                startActivity(Intent(this@MainActivity, IndicatorDemoActivity::class.java))

            }
        }
    }
}