package com.example.common.ui.view

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/***
 * 用以支持全局的iconfont资源的引用， 可以在布局文件中直接设置 text
 */
class IconFontTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attributeSet, defStyleAttr) {
    init {
        val typeface = Typeface.createFromAsset(context.assets, "/fonts/iconfont.ttf")
        setTypeface(typeface)
    }
}