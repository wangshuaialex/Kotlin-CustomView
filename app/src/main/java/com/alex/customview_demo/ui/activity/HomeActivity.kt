package com.alex.customview_demo.ui.activity

import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.alex.customview_demo.R
import com.alex.customview_demo.ui.fragment.CameraFragment
import com.alex.customview_demo.ui.fragment.CustomFragment


class HomeActivity : BaseActivity() {
    override fun initData() {
        //初始化设置展示CustomFragment
        var mFragmentTransaction = mFragmentManager.beginTransaction()
        mFragmentTransaction.add(obtainFrameLayout(), CustomFragment())
        mFragmentTransaction.commit();
    }

    override fun obtainFrameLayout(): Int {
        return R.id.fl_container
    }

    override fun generateLayout(): Int {
        return R.layout.activity_home
    }

    override fun switchItem(menuItem: MenuItem): Fragment {
        lateinit var mFragment: Fragment
        when (menuItem.itemId) {
            R.id.item_customView -> mFragment = CustomFragment()
            R.id.item_cameraView -> mFragment = CameraFragment()
        }
        return mFragment
    }
}