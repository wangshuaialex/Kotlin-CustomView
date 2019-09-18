package com.alex.customview_demo.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.alex.customview_demo.data.utils.DimensionUtils

/**
 * @author Alex
 * @date 2019/9/18.
 * GitHub：https://github.com/wangshuaialex
 */
class TypeEvalutorView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var province: String = "A"
        set(value) {
            field = value
            invalidate()
        }
        get() = field

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mPaint.textSize = DimensionUtils.dp2px(28F)
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null) {
            canvas.drawText(province, (width / 2).toFloat(), (height / 2).toFloat(), mPaint)
        }
    }
}