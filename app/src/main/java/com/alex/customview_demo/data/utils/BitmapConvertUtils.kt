package com.alex.customview_demo.data.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.alex.customview_demo.R

/**
 * @author Alex
 * @date 2019/9/11.
 * GitHub：https://github.com/wangshuaialex
 */
class BitmapConvertUtils {
    companion object {
        //获取头像
        fun getAvatarBitmap(resources: Resources, width: Int): Bitmap {
            var options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(resources, R.mipmap.icon_header, options)
            options.inSampleSize = 4
            options.inPreferredConfig = Bitmap.Config.RGB_565
            options.inJustDecodeBounds = false
            options.inDensity = options.outWidth
            options.inTargetDensity = width
            return BitmapFactory.decodeResource(resources, R.mipmap.icon_header, options);
        }
    }
}