package com.example.asproj.fragment.home

import android.os.Bundle
import android.util.Log
import android.util.SparseArray
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.asproj.R
import com.example.asproj.http.api.ApiFactory
import com.example.asproj.http.api.homeapi.HomeApi
import com.example.asproj.http.model.TabCategory
import com.example.common.ui.component.HiBaseFragment
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.callback.HiCallBack
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
                        updateUI(data)

                    }
                }

            })
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
        topTabLayout.addTabSelectedChangeListener { index, prevInfo, nextInfo ->
            Log.d("index", "$index")
            Log.d("currentItem", "${viewPager.currentItem}")
            //index  点击之后选中的那个下标
            if (viewPager.currentItem != index) {
                viewPager.setCurrentItem(index, false)
            }
        }
        viewPager.adapter = HomePagerAdapter(childFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, data)
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

    inner class HomePagerAdapter(fm: FragmentManager, behavior: Int, val tabs: List<TabCategory>) :
        FragmentPagerAdapter(fm, behavior) {
        private val fragments = SparseArray<Fragment>(tabs.size)

        override fun getItem(position: Int): Fragment {
            Log.d("lgm","创建fragment")
            var fragment = fragments.get(position, null)
            if (fragment == null) {
                fragment = HomeTabFragment.newInstance(tabs[position].categoryId)
                fragments.put(position, fragment)
            }
            return fragment
        }

        override fun getCount(): Int {
            return tabs.size
        }

    }

}