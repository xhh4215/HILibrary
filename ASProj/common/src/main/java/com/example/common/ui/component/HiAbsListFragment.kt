package com.example.common.ui.component

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.CallSuper
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common.R
import com.example.common.ui.view.EmptyView
import com.example.common.ui.view.HiRecyclerView
import com.example.hi_ui.ui.dataitem.HiAdapter
import com.example.hi_ui.ui.dataitem.HiDataItem
import com.example.hi_ui.ui.refresh.HiOverView
import com.example.hi_ui.ui.refresh.HiRefresh
import com.example.hi_ui.ui.refresh.HiRefreshLayout
import com.example.hi_ui.ui.refresh.HiTextOverView
import kotlinx.android.synthetic.main.fragment_list.*

open class HiAbsListFragment : HiBaseFragment(), HiRefresh.HiRefreshListener {
    var pageIndex: Int = 1
    private lateinit var hiAdapter: HiAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var refreshHeaderView: HiTextOverView
    private var refreshLayout: HiRefreshLayout? = null
    private var recyclerView: HiRecyclerView? = null
    private var emptyView: EmptyView? = null
    private var loadingView: ContentLoadingProgressBar? = null

    companion object {
        const val PREFETCH_SIZE = 5
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_list
    }

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.refreshLayout = refresh_layout
        this.recyclerView = recycler_view
        this.emptyView = empty_view
        this.loadingView = content_Loading
        refreshHeaderView = HiTextOverView(context)
        refreshLayout?.setRefreshOverView(refreshHeaderView)
        refreshLayout?.setRefreshListener(this)
        layoutManager = createLayoutManager()
        hiAdapter = HiAdapter(context!!)
        recyclerView?.layoutManager = layoutManager
        recyclerView?.adapter = hiAdapter
        emptyView?.visibility = View.GONE
        emptyView?.setIcon(R.string.if_detail)
        emptyView?.setDesc(getString(R.string.list_empty_desc))
        emptyView?.setRefresh(getString(R.string.list_empty_action), View.OnClickListener {
            onRefresh()
        })
        loadingView?.visibility = View.VISIBLE
        pageIndex = 1
    }


    fun finishRefresh(dataItem: List<HiDataItem<*, out RecyclerView.ViewHolder>>?) {
        val success = dataItem != null && dataItem.isNotEmpty()
        val refresh = pageIndex == 1
        if (refresh) {
            loadingView?.visibility = View.GONE
            refreshLayout?.refreshFinished()
            if (success) {
                emptyView?.visibility = View.GONE
                hiAdapter.clearItems()
                hiAdapter.addItems(dataItem!!, true)

            } else {
                //判断列表上是否已经有数据 没有 显示 emptyVIew
                if (hiAdapter.itemCount <= 0) {
                    emptyView?.visibility = View.VISIBLE
                }
            }
        } else {
            if (success) {
                hiAdapter.addItems(dataItem!!, true)
            }
            recyclerView?.loadFinished(success)
        }
    }

    fun enableLoadMore(callback: () -> Unit) {
         //这里可以直接这么写吗？
        //为了方式同时下拉刷新和上拉加载 的请求 此处该做处理
        recyclerView?.enableLoadMore({
            if (refreshHeaderView.state == HiOverView.HiRefreshState.STATE_REFRESH) {

                //正在刷新
                recyclerView?.loadFinished(false)
                return@enableLoadMore
            }
            pageIndex++
            callback()
        }, PREFETCH_SIZE)
    }


    fun disableLoadMore() {
        recyclerView?.disableLoadMore()
    }

    open fun createLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun enableRefresh(): Boolean {
        return true
    }

    @CallSuper
    override fun onRefresh() {
        if (recyclerView?.isLaidOut == true) {
            //正在分页
            refreshLayout?.post {
                refreshLayout?.refreshFinished()
            }
            return
        }
        pageIndex = 1
    }

}