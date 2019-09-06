package com.alex.customview_demo.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.alex.customview_demo.R
import com.alex.customview_demo.data.utils.DimensionUtils

/**
 * @author Alex
 * @date 2019/9/5.
 * GitHub：https://github.com/wangshuaialex
 */
class PieChartView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var mPaint = Paint();
    //半径大小
    val MRADIUS = DimensionUtils.dp2px(120f)
    //矩形
    var mRectF = RectF()

    //定义圆盘表颜色盘
    var mPieChartColor = arrayOf(
        resources.getColor(R.color.colorFirstPie),
        resources.getColor(R.color.colorSecondPie),
        resources.getColor(R.color.colorThirdPie)
    )
    //定义圆盘表每次圆弧的角度
    var mPieCharAngle: Array<Float> = arrayOf(120f, 120f, 120f)

    /*init {
        mRectF = RectF()
        mRectF.set(
            width / 2 - MRADIUS,
            height / 2 - MRADIUS,
            width / 2 + MRADIUS,
            height / 2 + MRADIUS
        )
    }*/

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRectF.set(
            width / 2 - MRADIUS,
            height / 2 - MRADIUS,
            width / 2 + MRADIUS,
            height / 2 + MRADIUS
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //起始角度
        var currentAngle: Float = 0f;
        //循环绘制每个圆弧
        for (index: Int in mPieChartColor.indices) {
            if (canvas != null) {
                //圆弧设置颜色
                mPaint.color = mPieChartColor[index]
                if (index == 2) {
                    canvas.save()
                    //对x、y的点坐标进行偏移
                    canvas.translate(
                        20 * Math.cos(Math.toRadians((currentAngle + mPieCharAngle[index] / 2).toDouble())).toFloat(),
                        20 * Math.sin(Math.toRadians((currentAngle + mPieCharAngle[index] / 2).toDouble())).toFloat()
                    )
                }
                //画圆弧
                canvas.drawArc(mRectF, currentAngle, mPieCharAngle[index], true, mPaint)
                //圆弧偏移
                currentAngle += mPieCharAngle[index]
                if (index == 2) {
                    canvas.restore()
                }
            }
        }
    }
}