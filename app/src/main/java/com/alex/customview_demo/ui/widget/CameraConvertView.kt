package com.alex.customview_demo.ui.widget

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.alex.customview_demo.data.utils.BitmapConvertUtils
import com.alex.customview_demo.data.utils.DimensionUtils

/**
 * @author Alex
 * @date 2019/9/11.
 * GitHub：https://github.com/wangshuaialex
 */
class CameraConvertView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    lateinit var mPaint: Paint
    lateinit var mCamera: Camera

    init {
        mPaint = Paint()
        mCamera = Camera()
        mCamera.rotateX(45f)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mCamera.applyToCanvas(canvas)
        var avatarBitmap =
            BitmapConvertUtils.getAvatarBitmap(resources, DimensionUtils.dp2px(20f).toInt())
        if (canvas != null) {
            canvas.drawBitmap(avatarBitmap, 0f, 0f, mPaint)
        }

    }

}