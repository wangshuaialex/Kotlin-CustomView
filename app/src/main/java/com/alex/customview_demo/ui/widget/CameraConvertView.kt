package com.alex.customview_demo.ui.widget

import android.content.Context
import android.content.res.Resources
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

    var mPaint: Paint
    var mCamera: Camera
    //定义宽度动画属性
    var mImageWidth: Float = 600F
        //手动设置set方法
        set(value) {
            field = value
            invalidate()
        }
        get() = field
    //定义折页顶部动画属性
    var mTopAngle: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
        get() = field
    //定义折页顶部动画属性
    var mBottomAngle: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
        get() = field

    //定义画布的折叠角度动画属性
    var mCanvasAngle: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
        get() = field

    init {
        mPaint = Paint()
        mCamera = Camera()
        mCamera.setLocation(0f, 0f, -6 * resources.displayMetrics.scaledDensity)
        //mCamera.rotateX(45f)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        //上半部分
        if (canvas != null) {
            canvas.save()
            mCamera.save()
            mCamera.rotateX(mTopAngle)
            //第一种方式：画布平移，平移完毕后针对图像进行重新切图
            canvas.translate(mImageWidth / 2, mImageWidth / 2)
            canvas.rotate(-mCanvasAngle)
            mCamera.applyToCanvas(canvas)
            canvas.clipRect(-mImageWidth / 2, -mImageWidth / 2, mImageWidth / 2, 0F)
            //第二种方式：画布不平移,直接切图像
            //canvas.clipRect(0f, 0f, mImageWidth, mImageWidth / 2)
            canvas.rotate(mCanvasAngle)
            canvas.translate(-mImageWidth / 2, -mImageWidth / 2)
            //图片绘制
            var avatarBitmap = BitmapConvertUtils.getAvatarBitmap(
                resources,
                DimensionUtils.dp2px(mImageWidth).toInt()
            )
            canvas.drawBitmap(avatarBitmap, 0f, 0f, mPaint)
            mCamera.restore()
            canvas.restore()
        }


        //下半部分
        if (canvas != null) {
            canvas.save()
            mCamera.save()
            mCamera.rotateX(mBottomAngle)
            //画布右下平移，位置重新变换，坐标系位置改动
            canvas.translate(mImageWidth / 2, mImageWidth / 2)
            mCamera.applyToCanvas(canvas)
            //canvas.rotate(-mCanvasAngle)
            //canvas.clipRect(0F, mImageWidth / 2, mImageWidth, mImageWidth)
            canvas.clipRect(-mImageWidth / 2, 0F, mImageWidth / 2, mImageWidth / 2)
            //canvas.rotate(mCanvasAngle)
            canvas.translate(-mImageWidth / 2, -mImageWidth / 2)
            //图片绘制
            var avatarBitmap =
                BitmapConvertUtils.getAvatarBitmap(
                    resources,
                    DimensionUtils.dp2px(mImageWidth).toInt()
                )
            canvas.drawBitmap(avatarBitmap, 0f, 0f, mPaint)
            mCamera.restore()
            canvas.restore()
        }
    }

}