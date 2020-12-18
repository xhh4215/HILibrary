package com.example.hi_dataitem

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.RuntimeException
import java.lang.reflect.ParameterizedType
import java.util.*


/***
 * @author 栾桂明
 * @date  2020年12月18
 */

class HiAdapter(val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //加载布局资源文件的对象
    private var mInflater: LayoutInflater = LayoutInflater.from(context)

    //存储显示的布局item的数据集合
    private var dataSets = ArrayList<HiDataItem<*, out RecyclerView.ViewHolder>>()

    //存储数据集合类型的集合
    private var typedArray = SparseArray<HiDataItem<*, out RecyclerView.ViewHolder>>()

    /***
     * 添加一个显示的item
     * index ：添加的位置
     * item：添加的item
     * notify：是否进行刷新
     */
    fun addItemAt(index: Int, item: HiDataItem<*, RecyclerView.ViewHolder>, notify: Boolean) {
        if (index > 0) {
            dataSets.add(index, item)
        } else {
            dataSets.add(item)
        }
        val notifies = if (index > 0) index else dataSets.size - 1
        if (notify) {
            notifyItemInserted(notifies)
        }
    }

    /***
     * 添加多个显示的item
     */
    fun addItems(items: List<HiDataItem<*, out RecyclerView.ViewHolder>>, notify: Boolean) {
        val start = dataSets.size

        for (item in items) {
            dataSets.add(item)
        }
        if (notify) {
            notifyItemRangeChanged(start, items.size)
        }
    }

    /***
     * 通过下标删除item
     */
    fun removeItemAt(index: Int): HiDataItem<*, out RecyclerView.ViewHolder>? {
        return if (index > 0 && index < dataSets.size) {
            val removeData = dataSets.removeAt(index)
            notifyItemRemoved(index)
            removeData
        } else {
            null
        }


    }

    /****
     * 通过item来进行删除
     */
    fun removeItem(item: HiDataItem<*, *>) {
        if (item != null) {
            val index = dataSets.indexOf(item)
            removeItemAt(index)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val dataItem = typedArray.get(viewType)
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

    /***
     * 获取DataItem的范型参数的类型同时对ViewHolder进行创建
     */
    private fun createViewHolderInternal(
        javaClass: Class<HiDataItem<*, out RecyclerView.ViewHolder>>,
        view: View?
    ): RecyclerView.ViewHolder {
        //获取父类
        val superclass = javaClass.genericSuperclass
        //判断是不是参数范型类型的
        if (superclass is ParameterizedType) {
            //获取范型参数集合
            val arguments = superclass.actualTypeArguments
            for (argument in arguments) {
                if (argument is Class<*> && RecyclerView.ViewHolder::class.java.isAssignableFrom(
                        argument
                    )
                ) {
                    return argument.getConstructor(View::class.java)
                        .newInstance(view) as RecyclerView.ViewHolder
                }
            }
        }
        return object : RecyclerView.ViewHolder(view!!) {

        }
    }

    /***
     * 获取视图显示的item的类型 通过这个类型进行不同的布局的加载
     */
    override fun getItemViewType(position: Int): Int {
        val dataItem = dataSets[position]
        val type = dataItem.javaClass.hashCode()
        /***
         * indexOfKey 返回值小于0则没有关联过
         */
        if (typedArray.indexOfKey(type) < 0) {
            typedArray.put(type, dataItem)
        }
        return type
    }

    override fun getItemCount() = dataSets.size

    fun getItem(position: Int): HiDataItem<*, RecyclerView.ViewHolder>? {
        if (position < 0 || position >= dataSets.size)
            return null
        return dataSets[position] as HiDataItem<*, RecyclerView.ViewHolder>
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.onBindData(holder, position)

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
             * spanSizeLookup
             * 通过 SpanSizeLookup()这个方法设置在使用网格布局的时候每个item占用的范围数
             * 默认是一
             */
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (position < dataSets.size) {
                        val hiDataItem = dataSets[position]
                        val spanSize = hiDataItem.getSpanSize()
                        return if (spanSize <= 0) spanCount else spanSize
                    }
                    return spanCount
                }

            }
        }
    }

    fun refreshTtem(hiDataItem: HiDataItem<*, *>) {
        val index = dataSets.indexOf(hiDataItem)
        notifyItemChanged(index)
    }


}

