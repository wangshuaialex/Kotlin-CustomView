package com.alex.customview_demo.ui.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alex.customview_demo.R
import com.alex.customview_demo.data.utils.ProvinceEvalutor
import com.alex.customview_demo.ui.widget.TypeEvalutorView
import kotlinx.android.synthetic.main.fragment_typeevalutor.*

/**
 * @author Alex
 * @date 2019/9/18.
 * GitHub：https://github.com/wangshuaialex
 */
class TypeEvalutorFragment : BaseFragment() {
    override fun loadData() {
        //参数4：结束值
        var animator =
            ObjectAnimator.ofObject(tev_evalutorView, "province", ProvinceEvalutor(), "G")
        animator.startDelay = 1000
        animator.duration = 3000
        animator.start()
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_typeevalutor
    }

    override fun initData() {
    }

}