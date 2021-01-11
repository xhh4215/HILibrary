package com.example.asproj.fragment.category

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.SparseIntArray
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.asproj.R
import com.example.asproj.http.api.ApiFactory
import com.example.asproj.http.api.categoryapi.CategoryApi
import com.example.asproj.http.model.Subcategory
import com.example.asproj.http.model.TabCategory
import com.example.common.ui.component.HiBaseFragment
import com.example.common.ui.view.EmptyView
import com.example.common.ui.view.loadUrl
import com.example.hi_library.restful.HiResponse
import com.example.hi_library.restful.callback.HiCallBack
import kotlinx.android.synthetic.main.fragment_category.*

/***
 * @desc  商品分类的界面
 * @author 栾桂明
 * @date 2020年 1月 7日
 */
class CategoryFragment : HiBaseFragment() {
    // 获取左侧的分类item失败的时候展示的空布局
    private var emptyView: EmptyView? = null
    private val SPAN_COUNT = 3

    /***
     * 设置当前界面使用的布局资源文件
     */
    override fun getLayoutId() = R.layout.fragment_category

    /***
     * 在onCreateView()调用之后调用的方法
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        queryCategoryList()
    }

    /***
     * 获取左侧的分类的item的数据
     */
    private fun queryCategoryList() {
        ApiFactory.create(CategoryApi::class.java).queryCategoryList()
            .enqueue(object : HiCallBack<List<TabCategory>> {
                override fun onSuccess(response: HiResponse<List<TabCategory>>) {
                    if (response.successfull() && response.data != null) {
                        onQueryCategoryListSuccess(response.data!!)
                    } else {
                        showEmptyView()
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    showEmptyView()
                }

            })
    }

    /***
     * 对查询成功的数据进行绑定
     */
    private fun onQueryCategoryListSuccess(data: List<TabCategory>) {
        if (!isAlive) return
        emptyView?.visibility = View.GONE
        slider_view.visibility = View.VISIBLE
        slider_view.bindMenuView(itemCount = data.size,
            onBindView = { holder, position ->
                val category = data[position]
                holder.findViewById<TextView>(R.id.menu_item_title)?.text = category.categoryName
            },
            onItemCLickListener = { holder, position ->
                val category = data[position]
                querySubcategoryList(category.categoryId)
            })
    }

    private fun querySubcategoryList(categoryId: String) {
        ApiFactory.create(CategoryApi::class.java).querySubcategoryList(categoryId)
            .enqueue(object : HiCallBack<List<Subcategory>> {
                override fun onSuccess(response: HiResponse<List<Subcategory>>) {
                    if (response.successfull() && response.data != null) {
                        onQuerySubcategoryListSuccess(response.data!!)

                    }
                }

                override fun onFailed(throwable: Throwable) {

                }

            })
    }

    private val layoutManager = GridLayoutManager(context, SPAN_COUNT)


    private val groupSpanSizeOffset = SparseIntArray()

    /***
     * 用来处理商品分组的自动换行
     */
    private val spanSizeLookUp = object : SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            var spanSize = 1
            val groupName = subcategoryList[position].groupName
            val nextGroupName =
                if (position + 1 < subcategoryList.size) subcategoryList[position + 1].groupName else null
            if (TextUtils.equals(groupName, nextGroupName)) {
                spanSize = 1
            } else {
                /****
                 * 1 当前组 position（所在组）在groupSpanSizeOffset中的索引下标
                 * 2 拿到，当前组前面一组 存储的spansizeoffset 偏移量
                 * 3 给当前组最后一个item 分配 spansize count
                 */
                val indexOfKey = groupSpanSizeOffset.indexOfKey(position)
                val size = groupSpanSizeOffset.size()
                val lastGroupOffset = if (size <= 0) 0
                else if (indexOfKey >= 0) {
                    // 说明当前的偏移量记录，已经存在了  groupSpanSizeOffset 这种情况发生在上下滑动
                    if (indexOfKey == 0) 0 else groupSpanSizeOffset.valueAt(indexOfKey - 1)
                } else {
                    // 说明当前组的偏移量记录 ，还没存在于 groupSpanSizeOffset 这种情况发生在第一次布局的时候
                    //得到前面所有组的偏移量之和
                    groupSpanSizeOffset.valueAt(size - 1)
                }

                spanSize = SPAN_COUNT - (position + lastGroupOffset) % SPAN_COUNT
                if (indexOfKey < 0) {
                    //得到当前组和前面所有组的spansize偏移量之和
                    val groupOffset = lastGroupOffset + spanSize - 1
                    groupSpanSizeOffset.put(position, groupOffset)
                }

            }
            return spanSize
        }


    }
    private val subcategoryList = mutableListOf<Subcategory>()
    private fun onQuerySubcategoryListSuccess(data: List<Subcategory>) {
        groupSpanSizeOffset.clear()
        subcategoryList.clear()
        subcategoryList.addAll(data)
        if (layoutManager.spanSizeLookup != spanSizeLookUp) {
            layoutManager.spanSizeLookup = spanSizeLookUp
        }
        slider_view.bindContentView(
            itemCount = data.size,
            itemDirection = null,
            layoutManager = layoutManager,
            onBindView = { holder, position ->
                val subcategory = data[position]
                holder.findViewById<ImageView>(R.id.content_item_image)
                    ?.loadUrl(subcategory.subcategoryIcon)
                holder.findViewById<TextView>(R.id.content_item_title)?.text =
                    subcategory.subcategoryName
            },
            onItemCLickListener = { holder, position ->
                // 是应该跳转到类目的商品列表页
                Toast.makeText(context, "you touch me${position}", Toast.LENGTH_SHORT).show()
            })
    }

    /***
     * 数据请求失败的展示的布局
     */
    private fun showEmptyView() {
        if (!isAlive) return
        if (emptyView == null) {
            emptyView = EmptyView(context!!)
            emptyView?.setIcon(R.string.if_empty3)
            emptyView?.setDesc(getString(R.string.list_empty_desc))
            emptyView?.setRefresh(getString(R.string.list_empty_action),
                View.OnClickListener { queryCategoryList() })
            emptyView?.setBackgroundColor(Color.WHITE)
            emptyView?.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
            root_container.addView(emptyView)
        }
        slider_view.visibility = View.GONE
        emptyView?.visibility = View.VISIBLE
    }
}