package com.example.asproj.biz.goods

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.example.asproj.fragment.home.GoodsItem
import com.example.asproj.http.api.ApiFactory
import com.example.asproj.http.api.goodsapi.GoodsApi
import com.example.asproj.http.model.GoodsList
import com.example.asproj.rote.HiRoute
import com.example.common.ui.component.HiAbsListFragment
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.callback.HiCallBack

/****
 * @date 2020年1月12日
 * @description 当前界面是一个对商品列表的数据的获取和处理的Fragment
 * @author  栾桂明
 */
class GoodsListFragment : HiAbsListFragment() {
    @JvmField
    @Autowired
    var subcategoryId: String = ""

    @JvmField
    @Autowired
    var categoryId: String = ""

    companion object {
        fun newInstance(categoryId: String, subcategoryId: String): Fragment {
            val args = Bundle()
            args.putString("categoryId", categoryId)
            args.putString("subcategoryId", subcategoryId)
            val fragment = GoodsListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    /***
     * 在OnCreate()结束之后的第一个执行的方法
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HiRoute.inject(this)
        //上拉加载更多
        enableLoadMore { loadData() }
        loadData()
    }

    /***
     * 下拉刷新
     */
    override fun onRefresh() {
        super.onRefresh()
        loadData()
    }

    /***
     * 发起网络请求获取数据
     */
    private fun loadData() {
        ApiFactory.create(GoodsApi::class.java)
            .queryCategoryGoodsList(categoryId, subcategoryId, 10, pageIndex)
            .enqueue(object : HiCallBack<GoodsList> {
                override fun onSuccess(response: HiResponse<GoodsList>) {
                    if (response.successfull() && response.data != null) {
                        onQueryCategoryListSuccess(response.data!!)

                    } else {
                        finishRefresh(null)
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    finishRefresh(null)
                }

            })

    }

    /***
     * 网络请求成功之后数据的处理
     */
    private fun onQueryCategoryListSuccess(data: GoodsList) {
        val dataItems = mutableListOf<GoodsItem>()
        for (goodModel in data.list) {
            val goodItem = GoodsItem(goodModel, false)
            dataItems.add(goodItem)

        }
        finishRefresh(dataItems)
    }

    /***
     * 修改当前界面的RecyclerView的布局方式
     */
    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context, 2)
    }
}