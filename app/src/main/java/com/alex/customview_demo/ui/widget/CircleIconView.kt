package com.alex.customview_demo.ui.widget

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.alex.customview_demo.R

/**
 * @author Alex
 * @date 2019/9/5.
 * GitHub：https://github.com/wangshuaialex
 */
class CircleIconView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var mPaint: Paint = Paint()
    //间距
    val BITMAPPADDING: Float = 20f
    //图像宽度
    val BITMAPWIDTH = 200
    //矩形
    lateinit var mRectF: RectF


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRectF = RectF()
        mRectF.set(
            BITMAPPADDING,
            BITMAPPADDING,
            BITMAPPADDING + BITMAPWIDTH,
            BITMAPPADDING + BITMAPWIDTH
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //头像
        if (canvas != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //设置离屏层
                var layerCount = canvas.saveLayer(mRectF, mPaint)
                //画圆
                canvas.drawOval(mRectF, mPaint)
                mPaint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
                canvas.drawBitmap(
                    getAvatarBitmap(BITMAPWIDTH),
                    BITMAPPADDING,
                    BITMAPPADDING,
                    mPaint
                )
                //清空内容
                mPaint.setXfermode(null)
                canvas.restoreToCount(layerCount)
            }
        }
    }

    fun getAvatarBitmap(width: Int): Bitmap {
        var options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.icon_header, options)
        options.inPreferredConfig = Bitmap.Config.RGB_565
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.mipmap.icon_header, options)
    }
}