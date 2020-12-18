package com.example.asproj.fragment.home

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.asproj.R
import com.example.hi_dataitem.HiDataItem
import com.example.hi_ui.ui.tab.top.HiTabTopInfo
import com.example.hi_ui.ui.tab.top.HiTabTopLayout

class HomeTopBarItem(homeItemData: HomeItemData, val context: Context) :
    HiDataItem<HomeItemData, HomeTopBarItem.HomeTopbarHolder>(homeItemData) {

    override fun onBindData(holder: HomeTopbarHolder, position: Int) {
        initTabLayout(holder)

    }

    private fun initTabLayout(holder: HomeTopbarHolder) {
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
        val infoList = arrayListOf<HiTabTopInfo<*>>()
        val defaultColor = context.resources.getColor(R.color.tabTopDefaultColor)
        val tintColor = context.resources.getColor(R.color.tabTopTintColor)
        array.forEach {
            val info = HiTabTopInfo(it, defaultColor, tintColor)
            infoList.add(info)
        }
        holder.hiTabTopLayout.inflateInfo(infoList)
        holder.hiTabTopLayout.addTabSelectedChangeListener { index, prevInfo, nextInfo ->
            // todo 点击tab的操作
            Toast.makeText(context, nextInfo.name, Toast.LENGTH_SHORT).show()
        }
        holder.hiTabTopLayout.defaultSelected(infoList.get(0))
    }

    override fun getItemLayoutRes() = R.layout.fragment_home_topbar_layout

    class HomeTopbarHolder(view: View) : RecyclerView.ViewHolder(view) {
        var hiTabTopLayout: HiTabTopLayout = view.findViewById(R.id.tab_top_layout)
        var viewPager: ViewPager2 = view.findViewById(R.id.tab_top_viewpager2)
    }


}