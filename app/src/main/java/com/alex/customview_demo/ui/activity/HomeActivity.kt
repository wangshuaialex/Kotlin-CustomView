package com.alex.customview_demo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import butterknife.ButterKnife
import butterknife.Unbinder
import com.alex.customview_demo.R
import com.alex.customview_demo.ui.adapter.SubHomeAdapter
import com.alex.customview_demo.ui.fragment.CircleIconFragment
import com.alex.customview_demo.ui.fragment.DashBoardFragment
import com.alex.customview_demo.ui.fragment.PieChartFragment
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {
    lateinit var mUnbinder: Unbinder


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Butterknife绑定
        mUnbinder = ButterKnife.bind(this)
        //Fragment集合
        var fragmentList = getFragmentList()
        var titleList = getTitleList()
        //设置适配器
        vp_homeContainer.adapter = SubHomeAdapter(supportFragmentManager, fragmentList, titleList)
        tab_homeContainer.setupWithViewPager(vp_homeContainer)
    }

    private fun getTitleList(): ArrayList<String> {
        var titleList = ArrayList<String>()
        titleList.add("仪表盘")
        titleList.add("饼状图")
        titleList.add("圆形头像")
        return titleList
    }

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

    override fun onDestroy() {
        super.onDestroy()
        mUnbinder.unbind()
    }
}
