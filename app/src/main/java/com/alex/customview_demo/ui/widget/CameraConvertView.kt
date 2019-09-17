package com.alex.customview_demo.ui.widget

import android.content.Context
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.alex.customview_demo.data.utils.BitmapConvertUtils
import com.alex.customview_demo.data.utils.DimensionUtils

/**
 * @author Alex
 * @date 2019/9/11.
 * GitHub：https://github.com/wangshuaialex
 */
class CameraConvertView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    var mPaint: Paint
    var mCamera: Camera
    val IMAGEWIDTH: Float = 400F

    init {
        mPaint = Paint()
        mCamera = Camera()
        mCamera.rotateX(45f)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //上半部分
        if (canvas != null) {
            canvas.save()
            //第一种方式：画布平移，平移完毕后针对图像进行重新切图
            //canvas.translate(IMAGEWIDTH / 2, IMAGEWIDTH / 2)
            //canvas.clipRect(-IMAGEWIDTH / 2, -IMAGEWIDTH / 2, IMAGEWIDTH / 2, 0F)
            // canvas.translate(-IMAGEWIDTH / 2, -IMAGEWIDTH / 2)
            //第二种方式：画布不平移,直接切图像
            canvas.clipRect(0f, 0f, IMAGEWIDTH, IMAGEWIDTH / 2)
            //图片绘制
            var avatarBitmap = BitmapConvertUtils.getAvatarBitmap(
                resources,
                DimensionUtils.dp2px(IMAGEWIDTH).toInt()
            )
            canvas.drawBitmap(avatarBitmap, 0f, 0f, mPaint)
            canvas.restore()
        }


        //下半部分
        if (canvas != null) {
            canvas.save()
            //画布右下平移，位置重新变换，坐标系位置改动
            canvas.translate(IMAGEWIDTH / 2, IMAGEWIDTH / 2)
            mCamera.applyToCanvas(canvas)
            canvas.clipRect(-IMAGEWIDTH / 2, 0F, IMAGEWIDTH / 2, IMAGEWIDTH / 2)
            canvas.translate(-IMAGEWIDTH / 2, -IMAGEWIDTH / 2)
            //图片绘制
            var avatarBitmap =
                BitmapConvertUtils.getAvatarBitmap(
                    resources,
                    DimensionUtils.dp2px(IMAGEWIDTH).toInt()
                )
            canvas.drawBitmap(avatarBitmap, 0f, 0f, mPaint)
            canvas.restore()
        }
    }

}