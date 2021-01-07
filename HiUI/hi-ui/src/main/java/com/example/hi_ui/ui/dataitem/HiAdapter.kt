package com.example.hi_ui.ui.dataitem

import android.content.Context
import android.util.Log
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.lang.RuntimeException
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType
import java.util.*


/***
 * @author 栾桂明
 * @date  2020年12月18
 */

class HiAdapter(context: Context) : RecyclerView.Adapter<ViewHolder>() {
    private var recyclerViewRef: WeakReference<RecyclerView>? = null

    //加载布局资源文件的对象
    private var mInflater = LayoutInflater.from(context)

    //存储显示的布局item的数据集合
    private var dataSets = ArrayList<HiDataItem<*, out ViewHolder>>()

    //存储数据集合类型的集合
//    private var typedArray = SparseArray<HiDataItem<*, out RecyclerView.ViewHolder>>()

    private val typePosition = SparseIntArray()

    //存储header的集合
    private var headers = SparseArray<View>()

    //存储footer的集合
    private var footers = SparseArray<View>()


    private var BASE_ITEM_TYPE_HEADER = 100000
    private var BASE_ITEM_TYPE_FOOTER = 200000

    /***
     * 添加headerView
     */
    fun addHeaderView(view: View) {
        if (headers.indexOfValue(view) < 0) {
            headers.put(BASE_ITEM_TYPE_HEADER++, view)
            notifyItemInserted(headers.size() - 1)
        }
    }

    /***
     * 添加footerView
     */
    fun addFooterView(view: View) {
         if (footers.indexOfValue(view) < 0) {
            footers.put(BASE_ITEM_TYPE_FOOTER++, view)
            notifyItemInserted(itemCount)
        }
    }

    /***
     * 删除headerView
     */
    fun removeHeaderView(view: View) {
        val indexOfValue = headers.indexOfValue(view)
        if (indexOfValue < 0) return
        headers.removeAt(indexOfValue)
        //position 代表的是在列表中的位置
        notifyItemRemoved(getHeaderSize() + getOriginalItemSize() + indexOfValue)
    }

    /***
     * 删除footerView
     */
    fun removeFooterView(view: View) {
        val indexOfValue = footers.indexOfValue(view)
        if (indexOfValue < 0) return
        footers.removeAt(indexOfValue)
        notifyItemRemoved(indexOfValue)
    }


    fun getHeaderSize(): Int {
        return headers.size()
    }

    fun getFooterSize(): Int {
        return footers.size()
    }

    fun getOriginalItemSize(): Int {
        return dataSets.size
    }


    /***
     * 添加一个显示的item
     * index ：添加的位置
     * item：添加的item
     * notify：是否进行刷新
     */
    fun addItemAt(index: Int, dataItem: HiDataItem<*, ViewHolder>, notify: Boolean) {
        if (index > 0) {
            dataSets.add(index, dataItem)
        } else {
            dataSets.add(dataItem)
        }
        val notifies = if (index > 0) index else dataSets.size - 1
        if (notify) {
            notifyItemInserted(notifies)
        }
        dataItem.setAdapter(this)
    }

    /***
     * 添加多个显示的item
     */
    fun addItems(items: List<HiDataItem<*, out ViewHolder>>, notify: Boolean) {
        val start = dataSets.size

        items.forEach { dataItem ->
            dataSets.add(dataItem)
            dataItem.setAdapter(this)
        }
        if (notify) {
            notifyItemRangeChanged(start, items.size)
        }
    }

    /***
     * 通过下标删除item
     */
    fun removeItemAt(index: Int): HiDataItem<*, out ViewHolder>? {
        return if (index > 0 && index < dataSets.size) {
            val removeData: HiDataItem<*, out ViewHolder> = dataSets.removeAt(index)
            notifyItemRemoved(index)
            removeData
        } else {
            null
        }


    }

    /****
     * 通过item来进行删除
     */
    fun removeItem(dataItem: HiDataItem<*, *>) {
        val index = dataSets.indexOf(dataItem)
        removeItemAt(index)
    }

    fun refreshTtem(hiDataItem: HiDataItem<*, *>) {
        val index = dataSets.indexOf(hiDataItem)
        notifyItemChanged(index)
    }

    /***
     * 获取视图显示的item的类型 通过这个类型进行不同的布局的加载
     */
    override fun getItemViewType(position: Int): Int {
        if (isHeaderPosition(position)) {
            return headers.keyAt(position)
        }
        if (isFooterPosition(position)) {
             //footer的位置要计算
            val footerPosition = position - getHeaderSize() - getOriginalItemSize()
            return footers.keyAt(footerPosition)
        }
        val itemPosition = position - getHeaderSize()
        val dataItem = dataSets[itemPosition]
        val type = dataItem.javaClass.hashCode()
        typePosition.put(type, position)
        return type
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):  ViewHolder {
        if (headers.indexOfKey(viewType) >= 0) {
            val view = headers[viewType]
            return object : RecyclerView.ViewHolder(view) {}
        }
        if (footers.indexOfKey(viewType) >= 0) {
            val view = footers[viewType]
            return object : RecyclerView.ViewHolder(view) {}
        }

        //这会导致不同position，但viewType相同，获取到的dataItem始终是第一次关联的dataItem对象。
        //这就会导致通过getItemView创建的成员变量，只在第一个dataItem中，其它实例中无法生效
        //为了解决dataItem成员变量binding, 刷新之后无法被复用的问题
        val position = typePosition.get(viewType)
        val dataItem = dataSets[position]
        val vh = dataItem.onCreateViewHolder(parent)
        if (vh != null) return vh
        var view: View? = dataItem.getItemView(parent)
        if (view == null) {
            val layoutRes = dataItem.getItemLayoutRes()
            if (layoutRes < 0) {
                RuntimeException("dataItem:${dataItem.javaClass.name}must override getItemView or getItemLayoutRes")
            }
            view = mInflater!!.inflate(layoutRes, parent, false)
        }
        return createViewHolderInternal(dataItem.javaClass, view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Footer 和Header 由View自行绑定
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return
        }
        val itemPosition = position - getHeaderSize()
        val dataItem = getItem(itemPosition)
        dataItem?.onBindData(holder, itemPosition)

    }


    /***
     * 获取DataItem的范型参数的类型同时对ViewHolder进行创建
     */
    private fun createViewHolderInternal(
        javaClass: Class<HiDataItem<*, out ViewHolder>>,
        view: View?
    ): ViewHolder {
        //获取父类
        val superclass = javaClass.genericSuperclass
        //判断是不是参数范型类型的
        if (superclass is ParameterizedType) {
            //获取范型参数集合
            val arguments = superclass.actualTypeArguments
            for (argument in arguments) {
                if (argument is Class<*> && ViewHolder::class.java.isAssignableFrom(
                        argument
                    )
                ) {
                    try {
                        //如果是，则使用反射 实例化类上标记的实际的泛型对象
                        //这里需要  try-catch 一把，如果咱们直接在HiDataItem子类上标记 RecyclerView.ViewHolder，抽象类是不允许反射的
                        return argument.getConstructor(View::class.java).newInstance(view) as ViewHolder
                    } catch (e: Throwable) {
                        e.printStackTrace()

                    }
                }
            }
        }
        return object : ViewHolder(view!!) {}
    }


    /***
     * 是否是Footer位置
     */
    private fun isFooterPosition(position: Int): Boolean {
        return position >= getHeaderSize() + getOriginalItemSize()

    }

    /***
     * 是否是Header位置
     */
    private fun isHeaderPosition(position: Int): Boolean {
        return position < headers.size()
    }

    /***
     * 获取列表中的item的个数
     */
    override fun getItemCount() = dataSets.size + getFooterSize() + getHeaderSize()

    fun getItem(position: Int): HiDataItem<*, RecyclerView.ViewHolder>? {
        if (position < 0 || position >= dataSets.size)
            return null
        return dataSets[position] as HiDataItem<*, RecyclerView.ViewHolder>
    }


    /***
     * 当adapter和recycleView相关联的时候回调的方法
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            //Returns the number of spans laid out by this grid.
            val spanCount = layoutManager.spanCount
            /***
             * 为列表上的item 适配网格布局
             * spanSizeLookup
             * 通过 SpanSizeLookup()这个方法设置在使用网格布局的时候每个item占用的范围数
             * 默认是一
             */
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (isHeaderPosition(position) || isFooterPosition(position)) {
                        return spanCount
                    }
                    val itemPosition = position - getHeaderSize()
                    if (itemPosition < dataSets.size) {
                        val hiDataItem = dataSets[position]
                        val spanSize = hiDataItem.getSpanSize()
                        return if (spanSize <= 0) spanCount else spanSize
                    }
                    return spanCount
                }

            }
        }
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        val position = holder.adapterPosition
        if (isHeaderPosition(position) || isFooterPosition(position))
            return
        val itemPosition = position - getHeaderSize()
        val dataItem = getItem(itemPosition) ?: return
        dataItem.onViewDetachedFromWindow(holder)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerViewRef?.clear()
    }

    open fun getAttachRecyclerView(): RecyclerView? {
        return recyclerViewRef?.get()
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val recyclerView = getAttachRecyclerView()
        if (recyclerView != null) {
            //瀑布流的item占比适配
            val position = recyclerView.getChildAdapterPosition(holder.itemView)
            val isHeaderFooter = isHeaderPosition(position) || isFooterPosition(position)
            val itemPosition = position - getHeaderSize()
            val dataItem = getItem(itemPosition) ?: return
            val lp = holder.itemView.layoutParams
            if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
                val manager = recyclerView.layoutManager as StaggeredGridLayoutManager?
                if (isHeaderFooter) {
                    lp.isFullSpan = true
                    return
                }
                val spanSize = dataItem.getSpanSize()
                if (spanSize == manager!!.spanCount) {
                    lp.isFullSpan = true
                }
            }

            dataItem.onViewAttachedToWindow(holder)
        }
    }

    fun clearItems() {
        dataSets.clear()
        notifyDataSetChanged()
    }


}

