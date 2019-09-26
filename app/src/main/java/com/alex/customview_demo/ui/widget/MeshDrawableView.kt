package com.alex.customview_demo.ui.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.alex.customview_demo.data.utils.DimensionUtils

/**
 * @author Alex
 * @date 2019/9/25.
 * GitHub：https://github.com/wangshuaialex
 */
class MeshDrawableView() : Drawable() {
    var mPaint: Paint
    //线间距
    var mInterval: Int

    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.strokeWidth = DimensionUtils.dp2px(2f)
        mPaint.color = Color.RED
        mInterval = DimensionUtils.dp2px(10f).toInt()
    }

    override fun draw(canvas: Canvas) {
        var bounds: Rect = bounds
        //在X轴坐标轴上画线，x坐标不变，y坐标改变
        for (xIndex in 0..bounds.right step mInterval) {
            canvas.drawLine(
                xIndex.toFloat(),
                bounds.top.toFloat(),
                xIndex.toFloat(),
                bounds.bottom.toFloat(),
                mPaint
            )
        }
        //在Y轴坐标轴上画线，y坐标不变,x坐标轴改变
        for (yIndex: Int in 0..bounds.bottom step mInterval) {
            canvas.drawLine(
                bounds.left.toFloat(),
                yIndex.toFloat(),
                bounds.right.toFloat(),
                yIndex.toFloat(),
                mPaint
            )
        }
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun getOpacity(): Int {
        //透明度的设置
        //255->不透明  OPAQUE
        //0->透明      TRANSPARENT
        //半透明       TRANSLUCENT
        return if (mPaint.alpha == 0xff) PixelFormat.OPAQUE else
            if (mPaint.alpha == 0) PixelFormat.TRANSPARENT else PixelFormat.TRANSLUCENT
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mPaint.setColorFilter(colorFilter)
    }
}