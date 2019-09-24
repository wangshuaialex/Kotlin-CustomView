package com.alex.customview_demo.ui.fragment

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.animation.TypeEvaluator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alex.customview_demo.R
import com.alex.customview_demo.data.utils.DimensionUtils
import com.alex.customview_demo.ui.widget.CameraConvertView
import kotlinx.android.synthetic.main.fragment_camera.*

/**
 * @author Alex
 * @date 2019/9/6.
 * GitHub：https://github.com/wangshuaialex
 */
class CameraFragment : BaseFragment() {
    override fun loadData() {
        //顶部折页动画
        var topAngleAnimator = ObjectAnimator.ofFloat(ccv_convertView, "mTopAngle", -60f)
        //画布折页动画
        var canvasAngleAnimator = ObjectAnimator.ofFloat(ccv_convertView, "mCanvasAngle", 270f)
        //底部折页动画
        var bottomAngleAnimator = ObjectAnimator.ofFloat(ccv_convertView, "mBottomAngle", 120f)
        var animatorSet = AnimatorSet()
        animatorSet.startDelay = 1000
        animatorSet.duration = 800
        animatorSet
            .playSequentially(bottomAngleAnimator)
        animatorSet.start()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_camera
    }

    override fun initData() {
    }
}