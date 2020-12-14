package com.example.hiui.tab

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hi_ui.ui.tab.bottom.HiTabBottomInfo
import com.example.hi_ui.ui.tab.bottom.HiTabBottomLayout
import com.example.hiui.R

class HiTabBottomActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_tab_bottom)
        initTabBottom()

    }

    private fun initTabBottom() {
        val hiTabBottomLayout = findViewById<HiTabBottomLayout>(R.id.hitablayout)

        hiTabBottomLayout.alpha = 0.85f
        val bottomInfoList: MutableList<HiTabBottomInfo<*>> = ArrayList()
        val homeInfo = HiTabBottomInfo(
            "首页", "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val recommendInfo = HiTabBottomInfo(
            "收藏", "fonts/iconfont.ttf",
            getString(R.string.if_recommend),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val categoryInfo = HiTabBottomInfo(
            "分类", "fonts/iconfont.ttf"
            , getString(R.string.if_category),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val chatInfo = HiTabBottomInfo(
            "推荐", "fonts/iconfont.ttf"
            , getString(R.string.if_chat),
            null,
            "#ff656667",
            "#ffd44949"
        )
        val profileInfo = HiTabBottomInfo(
            "我的", "fonts/iconfont.ttf"
            , getString(R.string.if_profile),
            null,
            "#ff656667",
            "#ffd44949"
        )
        bottomInfoList.add(homeInfo)
        bottomInfoList.add(categoryInfo)
        bottomInfoList.add(profileInfo)
        bottomInfoList.add(chatInfo)
        bottomInfoList.add(recommendInfo)
        hiTabBottomLayout.inflateInfo(bottomInfoList)
        hiTabBottomLayout.addTabSelectedChangeListener { _, _, nextInfo ->
            Toast.makeText(this@HiTabBottomActivity, nextInfo.name, Toast.LENGTH_SHORT).show()
        }
        hiTabBottomLayout.defaultSelected(homeInfo)

    }
}