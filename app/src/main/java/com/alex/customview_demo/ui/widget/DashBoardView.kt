package com.alex.customview_demo.ui.widget

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.alex.customview_demo.data.utils.DimensionUtils

/**
 * @author Alex
 * @date 2019/9/3.
 * GitHub：https://github.com/wangshuaialex
 */
class DashBoardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    //半径
    val mRadius = DimensionUtils.dp2px(100f)
    //椭圆外矩形
    val mRectF =
        RectF(width / 2 - mRadius, height / 2 - mRadius, width / 2 + mRadius, height / 2 + mRadius)
    //扇形角度
    val mArcAngle = 120
    //刻度条所依赖的线
    var mPath = Path()
    //刻度条
    lateinit var mPathDashPathEffect: PathDashPathEffect
    //刻度线数量
    val mDashCount: Int = 20


    init {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 3f
        //对仪表盘添加每一个小刻度矩形
        mPath.addRect(
            0F,
            0F,
            DimensionUtils.dp2px(3f),
            DimensionUtils.dp2px(8f),
            Path.Direction.CCW
        )


    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mPathDashPathEffect = PathDashPathEffect(
            mPath,
            PathMeasure(mPath, false).length - DimensionUtils.dp2px(3f) / mDashCount,
            0F,
            PathDashPathEffect.Style.ROTATE
        )

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var resources = resources

        if (canvas != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //先画原始的圆
                mPath.addArc(
                    width / 2 - mRadius,
                    height / 2 - mRadius,
                    width / 2 + mRadius,
                    height / 2 + mRadius,
                    (90 + mArcAngle / 2).toFloat(),
                    (360 - mArcAngle).toFloat()
                )

                canvas.drawPath(mPath, mPaint)
                //设置刻度条
                mPaint.setPathEffect(mPathDashPathEffect)
                //然后再画刻度条
                mPath.addArc(
                    width / 2 - mRadius,
                    height / 2 - mRadius,
                    width / 2 + mRadius,
                    height / 2 + mRadius,
                    (90 + mArcAngle / 2).toFloat(),
                    (360 - mArcAngle).toFloat()
                )
                canvas.drawPath(mPath, mPaint)
                mPaint.setPathEffect(null)
                //画指示器
                canvas.drawLine(
                    (width / 2).toFloat(),
                    (height / 2).toFloat(),
                    width / 2 + Math.cos(Math.toRadians(getAngle(5))).toFloat() * DimensionUtils.dp2px(
                        60f
                    ),
                    height / 2 + Math.sin(Math.toRadians(getAngle(5))).toFloat() * DimensionUtils.dp2px(
                        60f
                    ), mPaint
                )
            }
        }
    }

    fun getAngle(pCurrentPosition: Int): Double {
        return (90 + mArcAngle / 2 + (360 - mArcAngle) / mDashCount * pCurrentPosition).toDouble()
    }
}