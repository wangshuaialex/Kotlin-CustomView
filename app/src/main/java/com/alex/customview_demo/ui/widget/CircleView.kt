package com.alex.customview_demo.ui.widget

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

/**
 * @author Alex
 * @date 2019/9/3.
 * GitHub：https://github.com/wangshuaialex
 */
class CircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG);

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)


        canvas!!.drawCircle(
            (width / 2).toFloat(),
            (height / 2).toFloat(),
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                150F,
                Resources.getSystem().displayMetrics
            ), mPaint
        )
    }
}