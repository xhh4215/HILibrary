package com.example.hi_ui.ui.slider

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.provider.CalendarContract
import android.util.AttributeSet
import android.util.LayoutDirection
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.hi_ui.R
import com.example.hi_ui.ui.dataitem.HiViewHolder
import kotlinx.android.synthetic.main.hi_slider_menu_item.view.*

/****
 * @desc 这是一个侧滑分类的自定义View
 * @author 栾桂明
 * @see LinearLayout
 * @date 2020年1月7日
 */
class HiSliderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var menuItemAttr: MenuItemAttr

    //每个Item的默认的宽度
    private val MENU_WIDTH = applyUnit(TypedValue.COMPLEX_UNIT_DIP, 100f)

    //每个Item的默认的高度
    private val MENU_HEIGHT = applyUnit(TypedValue.COMPLEX_UNIT_DIP, 45f)

    //每个Item的默认的字体大小
    private val MENU_TEXT_SIZE = applyUnit(TypedValue.COMPLEX_UNIT_SP, 14f)

    //正常情况下的字体的颜色
    private val TEXT_COLOR_NORMAL = Color.parseColor("#666666")

    //选中情况下的字体的颜色
    private val TEXT_COLOR_SELECT = Color.parseColor("#DD3127")

    //正常情况下的背景颜色
    private val BG_COLOR_NORMAL = Color.parseColor("#F7F8F9")

    //选中情况下的背景颜色
    private val BG_COLOR_SELECT = Color.parseColor("#ffffff")

    //menuItem默认的布局样式id
    private val MENU_ITEM_LAYOUT_RES_ID = R.layout.hi_slider_menu_item

    //menuContent默认的布局样式id
    private val MENU_CONTENT_LAYOUT_RES_ID = R.layout.hi_slider_content_item

    //左侧的菜单视图
    val menuView = RecyclerView(context)

    //右侧的内容视图
    val contentView = RecyclerView(context)

    /****
     * 相当于Java中构造器内部执行的代码
     */
    init {
        menuItemAttr = parseMenuItemAttr(attrs)
        orientation = HORIZONTAL
        //通过layoutParam设置宽高
        menuView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        //去除动画
        menuView.itemAnimator = null
        //去除滑动到最底部没有数据的时候的阴影
        menuView.overScrollMode = View.OVER_SCROLL_NEVER

        //通过layoutParam设置宽高 宽度为MATCH_PARENT时会撑满右侧剩余的空间
        contentView.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        //去除动画
        contentView.itemAnimator = null
        //去除滑动到最底部没有数据的时候的阴影
        contentView.overScrollMode = View.OVER_SCROLL_NEVER

        addView(menuView)
        addView(contentView)


    }

    /***
     * 菜单栏布局item数据的绑定操作
     * @param  layoutRes 布局资源id
     * @param  itemCount  item的个数
     * @param onBindView 数据绑定的回调
     */
    fun bindMenuView(
        layoutRes: Int = MENU_ITEM_LAYOUT_RES_ID,
        itemCount: Int,
        onBindView: (HiViewHolder, Int) -> Unit,
        onItemCLickListener: (HiViewHolder, Int) -> Unit
    ) {
        menuView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        menuView.adapter = MenuAdapter(layoutRes, itemCount, onBindView, onItemCLickListener)
    }

    fun bindContentView(
        layoutRes: Int = MENU_CONTENT_LAYOUT_RES_ID,
        itemCount: Int,
        itemDirection: RecyclerView.ItemDecoration?,
        layoutManager: RecyclerView.LayoutManager,
        onBindView: (HiViewHolder, Int) -> Unit,
        onItemCLickListener: (HiViewHolder, Int) -> Unit
    ) {
        if (contentView.layoutManager == null) {
            contentView.layoutManager = layoutManager
            contentView.adapter = ContentAdapter(layoutRes)
            itemDirection?.let {
                contentView.addItemDecoration(itemDirection)
            }
        }
        val contentAdapter = contentView.adapter as ContentAdapter
        contentAdapter.update(itemCount, onBindView, onItemCLickListener)
        contentAdapter.notifyDataSetChanged()
        contentView.scrollToPosition(0)
    }


    inner class ContentAdapter(
        val layoutRes: Int
    ) : RecyclerView.Adapter<HiViewHolder>() {
        private lateinit var onBindView: (HiViewHolder, Int) -> Unit
        private lateinit var onItemCLickListener: (HiViewHolder, Int) -> Unit
        private var count: Int = 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiViewHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutRes, parent, false)
            val remainSize = width - paddingRight - paddingLeft - menuItemAttr.width
            val layoutManager = (parent as RecyclerView).layoutManager
            var spanCount = 0
            when (layoutManager) {
                is GridLayoutManager -> {
                    spanCount = layoutManager.spanCount
                }
                is StaggeredGridLayoutManager -> {
                    spanCount = layoutManager.spanCount
                }
            }
            if (spanCount>0){
                val itemWidth = remainSize/spanCount
                itemView.layoutParams = RecyclerView.LayoutParams(itemWidth,itemWidth)
            }
            return HiViewHolder(itemView)
        }

        override fun getItemCount() = count

        override fun onBindViewHolder(holder: HiViewHolder, position: Int) {
            onBindView(holder, position)
            holder.itemView.setOnClickListener {
                onItemCLickListener(holder, position)
            }
        }

        fun update(
            itemCount: Int,
            onBindView: (HiViewHolder, Int) -> Unit,
            onItemCLickListener: (HiViewHolder, Int) -> Unit
        ) {
            this.count = itemCount
            this.onBindView = onBindView
            this.onItemCLickListener = onItemCLickListener
        }

    }

    /***
     * item布局绑定的适配器
     */
    inner class MenuAdapter(
        val layoutRes: Int,
        val count: Int,
        val onBindView: (HiViewHolder, Int) -> Unit,
        val onItemCLickListener: (HiViewHolder, Int) -> Unit
    ) : RecyclerView.Adapter<HiViewHolder>() {
        private var currentSelectIndex = 0

        // 上一次选中的item
        private var lastSelectIndex = 0
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiViewHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutRes, parent, false)
            itemView.layoutParams =
                RecyclerView.LayoutParams(menuItemAttr.width, menuItemAttr.height)
            itemView.setBackgroundColor(menuItemAttr.normalBackGroundColor)
            itemView.findViewById<TextView>(R.id.menu_item_title)
                ?.setTextColor(menuItemAttr.textColor)
            itemView.findViewById<ImageView>(R.id.menu_item_indicator)
                ?.setImageDrawable(menuItemAttr.indicator)
            return HiViewHolder(itemView)
        }

        override fun getItemCount() = count

        override fun onBindViewHolder(holder: HiViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                currentSelectIndex = position
                notifyItemChanged(position)
                notifyItemChanged(lastSelectIndex)

            }
            //此处是为了实现进入页面就会又一个默认的选中的item
            if (currentSelectIndex == position) {
                onItemCLickListener(holder, position)
                lastSelectIndex = currentSelectIndex
            }

            applyItemAttr(position, holder)
            onBindView(holder, position)
        }

        /**
         * item的颜色状态的切换
         */
        private fun applyItemAttr(position: Int, holder: RecyclerView.ViewHolder) {
            //获取标识选中状态的Boolea值
            val selected = position == currentSelectIndex
            //获取标题View
            val titleView: TextView? = holder.itemView.menu_item_title
            //获取指示器View
            val indicatorView: ImageView? = holder.itemView.menu_item_indicator
            //设置指示器View的可见性
            indicatorView?.visibility = if (selected) View.VISIBLE else View.GONE
            //设置标题的文本大小
            titleView?.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                if (selected) menuItemAttr.selectTextSize.toFloat() else menuItemAttr.textSize.toFloat()
            )
            //设置标题的背景颜色
            titleView?.setBackgroundColor(if (selected) menuItemAttr.selectBackgroundColor else menuItemAttr.normalBackGroundColor)
            titleView?.isSelected = selected

        }

    }

    /****
     * 解析XML文件中定义的自定义属性的方法
     */
    private fun parseMenuItemAttr(attrs: AttributeSet?): MenuItemAttr {
        //获取设置样式的数组
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.HiSliderView)
        //获取item的宽度
        val menuItemWidth =
            typeArray.getDimensionPixelOffset(R.styleable.HiSliderView_menuItemWidth, MENU_WIDTH)
        //获取item的高度
        val menuItemHeight =
            typeArray.getDimensionPixelOffset(R.styleable.HiSliderView_menuItemHeight, MENU_HEIGHT)
        //获取item的默认字体的大小
        val menuItemTextSize = typeArray.getDimensionPixelSize(
            R.styleable.HiSliderView_menuItemTextSize,
            MENU_TEXT_SIZE
        )
        //获取item的选中的字体的大小
        val menuItemSelectTextSize = typeArray.getDimensionPixelSize(
            R.styleable.HiSliderView_menuItemSelectTextSize,
            MENU_TEXT_SIZE
        )
        // 获取menuItem的颜色数组
        val menuItemTextColor =
            typeArray.getColorStateList(R.styleable.HiSliderView_menuItemTextColor)
                ?: generateColorList()
        //获取menuItem指示器
        val menuItemIndicator = typeArray.getDrawable(R.styleable.HiSliderView_menuItemIndicator)
            ?: ContextCompat.getDrawable(context, R.drawable.shape_hi_silder_indicator)
        //默认的背景颜色
        val menuItemBackgroundColor =
            typeArray.getColor(R.styleable.HiSliderView_menuItemBackgroundColor, BG_COLOR_NORMAL)
        //选中的时候的背景颜色
        val menuItemSelectBackgroundColor = typeArray.getColor(
            R.styleable.HiSliderView_menuItemSelectBackgroundColor,
            BG_COLOR_SELECT
        )
        //回收数组
        typeArray.recycle()
        return MenuItemAttr(
            menuItemWidth,
            menuItemHeight,
            menuItemTextColor,
            menuItemSelectBackgroundColor,
            menuItemBackgroundColor,
            menuItemTextSize,
            menuItemSelectTextSize,
            menuItemIndicator
        )
    }

    /**
     * 封装侧滑组件的属性的对象
     */
    data class MenuItemAttr(
        val width: Int,
        val height: Int,
        val textColor: ColorStateList,
        val selectBackgroundColor: Int,
        val normalBackGroundColor: Int,
        val textSize: Int,
        val selectTextSize: Int,
        val indicator: Drawable?
    )

    /***
     * 获取一个标识选中和未选中状态的颜色切换的 ColorStateList对象
     */
    private fun generateColorList(): ColorStateList {
        // 存储状态的数组
        val states = Array(2) { IntArray(2) }
        // 存储颜色的数组
        val colors = IntArray(2)
        //选中颜色
        colors[0] = TEXT_COLOR_SELECT
        //默认颜色
        colors[1] = TEXT_COLOR_NORMAL
        states[0] = IntArray(1) { android.R.attr.state_selected }
        states[1] = IntArray(1)

        return ColorStateList(states, colors)

    }


    /***
     * 将数值转化为可以在布局文件中使用的数值 例如  sp  dp
     */
    private fun applyUnit(unit: Int, value: Float): Int {
        return TypedValue.applyDimension(unit, value, resources.displayMetrics).toInt()
    }
}

