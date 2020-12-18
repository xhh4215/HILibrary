package com.example.hiui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.hi_ui.ui.tab.top.HiTabTopInfo
import com.example.hi_ui.ui.tab.top.HiTabTopLayout
import java.util.ArrayList

class TabTopDemoActivity : AppCompatActivity() {
    val array = arrayOf(
        "热门",
        "服装",
        "数码",
        "鞋子",
        "零食",
        "家电",
        "汽车",
        "百货",
        "家居",
        "装修",
        "运动"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_top_demo)
        initTabTop()

    }

    private fun initTabTop() {
        val hiTabTopLayout = findViewById<HiTabTopLayout>(R.id.tab_top_layout)
        val infoList = arrayListOf<HiTabTopInfo<*>>()
        val defaultColor = resources.getColor(R.color.tabTopDefaultColor)
        val tintColor = resources.getColor(R.color.tabTopTintColor)
        array.forEach {
            val info = HiTabTopInfo(it, defaultColor, tintColor)
            infoList.add(info)
        }
        hiTabTopLayout.inflateInfo(infoList)
        hiTabTopLayout.addTabSelectedChangeListener { index, prevInfo, nextInfo ->
            Toast.makeText(
                this@TabTopDemoActivity,
                nextInfo.name,
                Toast.LENGTH_SHORT
            ).show()
        }
        hiTabTopLayout.defaultSelected(infoList.get(0))
    }

}