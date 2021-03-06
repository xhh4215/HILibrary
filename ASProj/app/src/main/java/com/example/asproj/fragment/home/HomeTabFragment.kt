package com.example.asproj.fragment.home

import android.os.Bundle
import android.text.TextUtils
 import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asproj.http.api.ApiFactory
import com.example.asproj.http.api.homeapi.HomeApi
import com.example.asproj.http.model.HomeModel
import com.example.common.ui.component.HiAbsListFragment
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.annotation.CacheStrategy
import com.example.hi_library.restful.annotation.CacheStrategy.Companion.CACHE_FIRST
import com.example.hi_library.restful.annotation.CacheStrategy.Companion.NET_CACHE
import com.example.hi_library.restful.annotation.CacheStrategy.Companion.NET_ONLY
import com.example.hi_library.restful.callback.HiCallBack
import com.example.hi_ui.ui.dataitem.HiDataItem

class HomeTabFragment : HiAbsListFragment() {
    private var categoryId: String? = null
    private val DEFAULT_HOT_TAB_CATEGORY_ID = "1"

    companion object {
        fun newInstance(categoryId: String): HomeTabFragment {
            val args = Bundle()
            args.putString("categoryId", categoryId)
            val fragment = HomeTabFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        categoryId = arguments?.getString("categoryId", DEFAULT_HOT_TAB_CATEGORY_ID)
        super.onViewCreated(view, savedInstanceState)
        queryTabCategoryList(CACHE_FIRST)
        enableLoadMore {
            queryTabCategoryList(NET_ONLY)
        }
    }

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        val isHotTab = TextUtils.equals(categoryId, DEFAULT_HOT_TAB_CATEGORY_ID)
        return if (isHotTab) super.createLayoutManager() else GridLayoutManager(context, 2)

    }

    /***
     * 下拉刷新
     */
    override fun onRefresh() {
        super.onRefresh()
        queryTabCategoryList(NET_CACHE)
    }


    private fun queryTabCategoryList(cacheStrategy: Int) {
        ApiFactory.create(HomeApi::class.java).queryTabCategoryList(cacheStrategy,categoryId!!, pageIndex, 10)
            .enqueue(object : HiCallBack<HomeModel> {
                override fun onSuccess(response: HiResponse<HomeModel>) {
                    if (response.successfull() && response.data != null) {
                        updateUI(response.data!!)
                    } else {
                        finishRefresh(null)
                    }
                }

                override fun onFailed(throwable: Throwable) {
                     //空数据页面
                    finishRefresh(null)
                }

            })

    }

    private fun updateUI(data: HomeModel) {
        if (!isAlive) return
        val dataItems = mutableListOf<HiDataItem<*, *>>()
        data.bannerList?.let {
            dataItems.add(BannerItem(data.bannerList))
        }
        data?.subcategoryList?.let {
            dataItems.add(GridItem(data.subcategoryList))
        }
         data.goodsList?.forEachIndexed { index, goodsModel ->
             dataItems.add(
                GoodsItem(
                    goodsModel,
                    TextUtils.equals(categoryId, DEFAULT_HOT_TAB_CATEGORY_ID)
                )
            )
        }

        finishRefresh(dataItems)

    }

}