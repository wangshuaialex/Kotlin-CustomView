package com.alex.customview_demo.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

/**
 * @author Alex
 * @date 2019/9/6.
 * GitHub：https://github.com/wangshuaialex
 */
class SubHomeAdapter(
    fm: FragmentManager?,
    fragmentList: ArrayList<Fragment>,
    titleList: ArrayList<String>
) : FragmentPagerAdapter(fm) {

    var fragmentList = fragmentList
    var titleList = titleList

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList.get(position)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titleList.get(position)
    }
}