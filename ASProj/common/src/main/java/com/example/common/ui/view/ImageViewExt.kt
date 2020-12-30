package com.example.common.ui.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.widget.ImageView
import com.bumptech.glide.Glide
 import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

/***
 * 加载普通的图片
 */
fun ImageView.loadUrl(url: String) {
    Glide.with(this).load(url).into(this)
}

/***
 * 加载圆形图片
 */
fun ImageView.loadCircle(url: String) {
    Glide.with(this).load(url).transform(CenterCrop()).into(this)
}

/***
 * 加载圆角
 */
fun ImageView.loadCorner(url: String, corner: Int) {
    Glide.with(this).load(url).transform(CenterCrop(), RoundedCorners(corner)).into(this)

}

class CircleBorderTransform(val borderWidth: Float, val borderColor: Int) : CircleCrop() {
    val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {

        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderWidth
        borderPaint.setColor(borderColor)

    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val transform = super.transform(pool, toTransform, outWidth, outHeight)
        val canvas = Canvas(transform)
        val halfwidth = outWidth / 2.toFloat()
        val halfHeight = outHeight / 2.toFloat()
        canvas.drawCircle(
            halfwidth,
            halfHeight,
            Math.min(halfHeight, halfwidth) - borderWidth,
            borderPaint
        )
        canvas.setBitmap(null)
        return transform
    }
}
