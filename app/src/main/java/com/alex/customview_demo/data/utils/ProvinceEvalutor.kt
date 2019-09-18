package com.alex.customview_demo.data.utils

import android.animation.TypeEvaluator
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Alex
 * @date 2019/9/18.
 * GitHub：https://github.com/wangshuaialex
 */
class ProvinceEvalutor : TypeEvaluator<String> {
    var provinceList: ArrayList<String>? = ArrayList<String>()
    init {
        provinceList!!.add("A")
        provinceList!!.add("B")
        provinceList!!.add("C")
        provinceList!!.add("D")
        provinceList!!.add("E")
        provinceList!!.add("F")
        provinceList!!.add("G")
    }


    override fun evaluate(fraction: Float, startValue: String?, endValue: String?): String {
        var startIndex: Int = provinceList!!.indexOf(startValue)
        var endIndex: Int = provinceList!!.indexOf(endValue)
        //据源码可知，计算结果为：result = x0 + t * (x1 - x0)
        //x0 is startValue
        //x1 is endValue
        //t is fraction
        var currentIndex = startIndex + fraction * (endIndex - startIndex)
        return provinceList!!.get(currentIndex.toInt())
    }
}