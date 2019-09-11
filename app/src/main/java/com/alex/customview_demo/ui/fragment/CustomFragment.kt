package com.alex.customview_demo.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import butterknife.ButterKnife
import butterknife.Unbinder
import com.alex.customview_demo.R
import com.alex.customview_demo.ui.adapter.SubHomeAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_custom.*

/**
 * @author Alex
 * @date 2019/9/6.
 * GitHub：https://github.com/wangshuaialex
 */
class CustomFragment : Fragment() {
    lateinit var mUnbinder: Unbinder
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView =
            LayoutInflater.from(context).inflate(R.layout.fragment_custom, container, false)
        var vp_homeContainer = rootView.findViewById<ViewPager>(R.id.vp_homeContainer)
        var tab_homeContainer = rootView.findViewById<TabLayout>(R.id.tab_homeContainer)
        //Fragment集合
        var fragmentList = getFragmentList()
        var titleList = getTitleList()
        //设置适配器
        vp_homeContainer.offscreenPageLimit = 2
        vp_homeContainer.adapter = SubHomeAdapter(childFragmentManager, fragmentList, titleList)
        tab_homeContainer.setupWithViewPager(vp_homeContainer)
        return rootView
    }

    /**
     * 获取标题集合
     */
    private fun getTitleList(): ArrayList<String> {
        var titleList = ArrayList<String>()
        titleList.add("仪表盘")
        titleList.add("饼状图")
        titleList.add("圆形头像")
        return titleList
    }

    /**
     * 获取Fragment条目集合
     */
    private fun getFragmentList(): ArrayList<Fragment> {
        var fragmentList = ArrayList<Fragment>()
        var dashBoardFragment = DashBoardFragment()
        var pieChartFragment = PieChartFragment()
        var circleIconFragment = CircleIconFragment()
        fragmentList.add(dashBoardFragment)
        fragmentList.add(pieChartFragment)
        fragmentList.add(circleIconFragment)
        return fragmentList
    }

}