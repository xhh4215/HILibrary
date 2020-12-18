package com.example.hiui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.example.hi_ui.ui.tab.common.IHiTabLayout
import com.example.hi_ui.ui.tab.top.HiTabTopInfo
import com.example.hi_ui.ui.tab.top.HiTabTopLayout

// android  应用的进程入口是ActivityThread

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var toptab: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toptab = findViewById(R.id.topTab)
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
            R.id.banner -> {
                startActivity(Intent(this@MainActivity, IndicatorDemoActivity::class.java))

            }
        }
    }


    override fun onResume() {
        super.onResume()
        //无法正确获取宽高
        Log.d("onResume", "${toptab.width}======${toptab.height}")
        toptab.post {
            Log.d("onResume:post", "${toptab.width}======${toptab.height}")
        }
        //每次View树发生变化都会回调到这里
        toptab.viewTreeObserver.addOnGlobalLayoutListener {
            //通过以下方式避免多次回调

            Log.d("onResume:view", "${toptab.width}======${toptab.height}")

        }
    }
}