package com.alex.customview_demo.data.utils

import android.content.res.Resources
import android.util.TypedValue
import java.lang.reflect.Type

/**
 * @author Alex
 * @date 2019/9/3.
 * GitHub：https://github.com/wangshuaialex
 */
class DimensionUtils {

    companion object {

        //dp转px，适配不同手机
        open fun dp2px(cdip: Float): Float {
            return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                cdip,
                Resources.getSystem().displayMetrics
            )
        }
    }

}