package com.example.hi_ui.ui.banner.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.example.hi_library.utils.HiDisplayUtil
import com.example.hi_ui.R

class HiCircleIndicatorKt @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr), HiIndicator<FrameLayout> {
    companion object {
        private const val VWC = ViewGroup.LayoutParams.WRAP_CONTENT
    }


    /***
     * 正常状态下的指示点
     */
    @DrawableRes
    val mPointNormal = R.drawable.shape_point_normal

    /***
     * 选中状态下的指示点
     */
    @DrawableRes
    val mPointSelected = R.drawable.shape_point_select

    /***
     * 指示点左右内间距
     */
    private var mPointLeftRightPadding = 0

    /***
     * 指示点上下内间距
     */
    private var mPointTopBottomPadding = 0

    init {
        mPointLeftRightPadding = HiDisplayUtil.dp2px(5f, getContext().resources)
        mPointTopBottomPadding = HiDisplayUtil.dp2px(15f, getContext().resources)

    }

    override fun get(): FrameLayout {
        return this
    }

    override fun onInflate(count: Int) {
        removeAllViews()
        if (count <= 0) {
            return
        }
    }

    override fun onPointChange(current: Int, count: Int) {
        TODO("Not yet implemented")
    }

}