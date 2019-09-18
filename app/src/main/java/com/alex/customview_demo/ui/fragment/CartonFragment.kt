package com.alex.customview_demo.ui.fragment

import android.graphics.Camera
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.alex.customview_demo.R
import com.alex.customview_demo.ui.adapter.SubHomeAdapter
import com.google.android.material.tabs.TabLayout

/**
 * @author Alex
 * @date 2019/9/18.
 * GitHub：https://github.com/wangshuaialex
 */
class CartonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView =
            LayoutInflater.from(context).inflate(R.layout.fragment_carton, container, false)
        var vp_cartonContainer = rootView.findViewById<ViewPager>(R.id.vp_cartonContainer)
        var tab_cartonContainer = rootView.findViewById<TabLayout>(R.id.tab_cartonContainer)
        //Fragment集合
        var fragmentList = getFragmentList()
        var titleList = getTitleList()
        //设置适配器
        vp_cartonContainer.offscreenPageLimit = 1
        vp_cartonContainer.adapter = SubHomeAdapter(childFragmentManager, fragmentList, titleList)
        tab_cartonContainer.setupWithViewPager(vp_cartonContainer)
        return rootView
    }

    /**
     * 获取标题集合
     */
    private fun getTitleList(): ArrayList<String> {
        var titleList = ArrayList<String>()
        titleList.add("折页效果")
        titleList.add("插值器实现动画")
        return titleList
    }

    /**
     * 获取Fragment条目集合
     */
    private fun getFragmentList(): ArrayList<Fragment> {
        var fragmentList = ArrayList<Fragment>()
        var cameraFragment = CameraFragment()
        var typeEvalutorFragment = TypeEvalutorFragment()
        fragmentList.add(cameraFragment)
        fragmentList.add(typeEvalutorFragment)
        return fragmentList
    }
}