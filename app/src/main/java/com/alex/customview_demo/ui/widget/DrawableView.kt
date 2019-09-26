package com.alex.customview_demo.ui.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
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
class DrawableView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    var drawable: Drawable

    init {
        drawable = ColorDrawable(Color.BLUE)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas != null) {
            canvas.save()
            var meshDrawableView = MeshDrawableView()
            //必须设置宽和高
            meshDrawableView.setBounds(0, 0, width, height)
            meshDrawableView.draw(canvas)
            canvas.restore()
        }
    }
}