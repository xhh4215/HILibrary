package com.example.asproj.fragment.home

import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.asproj.R
import com.example.asproj.http.api.ApiFactory
import com.example.asproj.http.api.homeapi.HomeApi
import com.example.asproj.http.model.TabCategory
import com.example.common.ui.component.HiBaseFragment
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.callback.HiCallBack
import com.example.hi_ui.ui.tab.bottom.HiTabBottomLayout
import com.example.hi_ui.ui.tab.common.IHiTabLayout
import com.example.hi_ui.ui.tab.top.HiTabTopInfo
import kotlinx.android.synthetic.main.fragment_home.*

class HomePageFragment : HiBaseFragment() {
    private var topTabSelectIndex = 0
    private val DEFAULT_SELECT_INDEX = 0
    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HiTabBottomLayout.clipBottomPadding(view_pager)
        queryTabList()
    }

    private fun queryTabList() {
        ApiFactory.create(HomeApi::class.java).queryTabList()
            .enqueue(object : HiCallBack<List<TabCategory>> {
                override fun onFailed(throwable: Throwable) {

                }

                override fun onSuccess(response: HiResponse<List<TabCategory>>) {
                    val data = response.data
                    if (response.successfull() && data != null) {
                        // 一次缓存数据  一次接口数据
                        updateUI(data)

                    }
                }

            })
    }

    private val onTabSelectedListener =
        IHiTabLayout.OnTabSelectedListener<HiTabTopInfo<*>> { index, prevInfo, nextInfo ->
            if (view_pager.currentItem != index) {
                view_pager.setCurrentItem(index, false)
            }
        }

    private fun updateUI(data: List<TabCategory>) {
        //需要消息处理一下
        if (!isAlive) return
        val topTabs = mutableListOf<HiTabTopInfo<Int>>()
        data.forEachIndexed { index, tabCategory ->
            val defaultColor = ContextCompat.getColor(context!!, R.color.color_333)
            val selectColor = ContextCompat.getColor(context!!, R.color.color_dd2)
            val tabTopInfo = HiTabTopInfo<Int>(tabCategory.categoryName, defaultColor, selectColor)
            topTabs.add(tabTopInfo)
        }
        val viewPager = view_pager
        val topTabLayout = top_tab_layout
        topTabLayout.inflateInfo(topTabs as List<HiTabTopInfo<*>>)
        topTabLayout.defaultSelected(topTabs[DEFAULT_SELECT_INDEX])
        topTabLayout.addTabSelectedChangeListener(onTabSelectedListener)
        if (viewPager.adapter == null) {
            viewPager.adapter = HomePagerAdapter(
                childFragmentManager,
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
            )
            viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
                override fun onPageSelected(position: Int) {
                    //  切换tab  手动翻页
                    if (position != topTabSelectIndex) {

                        //  通知topTabLayout进行切换
                        topTabLayout.defaultSelected(topTabs[position])
                        topTabSelectIndex = position
                    }
                }
            })
        }
        (viewPager.adapter as HomePagerAdapter).update(data)

    }

    inner class HomePagerAdapter(fm: FragmentManager, behavior: Int) :
        FragmentPagerAdapter(fm, behavior) {
        val tabs = mutableListOf<TabCategory>()
        private val fragments = SparseArray<Fragment>(tabs.size)

        override fun getItem(position: Int): Fragment {
            Log.d("lgm", "创建fragment")
            val categoryId = tabs[position].categoryId
            val categoryIdKey = categoryId.toInt()
            var fragment = fragments.get(categoryIdKey, null)
            if (fragment == null) {
                fragment = HomeTabFragment.newInstance(tabs[position].categoryId)
                fragments.put(categoryIdKey, fragment)
            }
            return fragment
        }

        override fun getCount(): Int {
            return tabs.size
        }

        override fun getItemPosition(`object`: Any): Int {
            //
            val indexOfValue = fragments.indexOfValue(`object` as Fragment)
            val fragment = getItem(indexOfValue)
            return if (fragment == `object`) {
                PagerAdapter.POSITION_UNCHANGED
            } else
                PagerAdapter.POSITION_NONE
        }

        override fun getItemId(position: Int): Long {
            return tabs[position].categoryId.toLong()
        }

        fun update(list: List<TabCategory>) {
            tabs.clear()
            tabs.addAll(list)
            notifyDataSetChanged()
        }
    }

}