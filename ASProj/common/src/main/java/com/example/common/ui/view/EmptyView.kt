package com.example.common.ui.view

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import com.example.common.R
import org.w3c.dom.Text
import java.util.jar.Attributes

class EmptyView : LinearLayout {
    private var icon: TextView
    private var title: TextView
    private var button: Button

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyle: Int) : super(
        context,
        attributeSet,
        defStyle
    ) {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        LayoutInflater.from(context).inflate(R.layout.layout_empty_view, this, true)
        icon = findViewById(R.id.empty_icon)
        button = findViewById(R.id.empty_action)
        title = findViewById(R.id.empty_text)
        var typeface = Typeface.createFromAsset(context.assets, "fonts/iconfont.ttf")
        icon.typeface = typeface
    }


    fun setIcon(@StringRes iconRes: Int) {
        icon.setText(iconRes)
    }


    fun setTitle(text: String) {
        title.text = text
        title.visibility = if (TextUtils.isDigitsOnly(text)) View.GONE else View.VISIBLE


    }

    fun setButton(text: String, listener: OnClickListener) {
        if (TextUtils.isEmpty(text)) {
            button.visibility = View.GONE
        } else {
            button.visibility = View.VISIBLE
            button.setOnClickListener(listener)
        }

    }

}