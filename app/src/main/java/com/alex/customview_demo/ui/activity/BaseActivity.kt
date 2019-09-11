package com.alex.customview_demo.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import butterknife.ButterKnife
import butterknife.Unbinder
import com.alex.customview_demo.R
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_home.*

/**
 * @author Alex
 * @date 2019/9/11.
 * GitHub：https://github.com/wangshuaialex
 */
abstract class BaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var mFragmentManager: FragmentManager


    lateinit var mUnbinder: Unbinder
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(generateLayout())
        //Butterknife绑定
        mUnbinder = ButterKnife.bind(this)
        //上下文
        mContext = this;
        //添加设置Fragment
        mFragmentManager = supportFragmentManager
        //关联Toolbar与DrawerLayout
        concactNavigationView()
        //初始化数据相关
        initData()
    }

    abstract fun initData()

    abstract fun obtainFrameLayout(): Int

    abstract fun generateLayout(): Int

    abstract fun switchItem(menuItem: MenuItem): Fragment

    override fun onDestroy() {
        super.onDestroy()
        mUnbinder.unbind()
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        var mFragmentTransaction = mFragmentManager.beginTransaction()
        var fragment: Fragment = switchItem(menuItem);
        for (mFragment in mFragmentManager.fragments) {
            mFragmentTransaction.hide(mFragment)
        }
        if (!fragment.isAdded) {
            mFragmentTransaction.add(obtainFrameLayout(), fragment)
            mFragmentTransaction.show(fragment)
        } else {
            mFragmentTransaction.show(fragment)
        }
        mFragmentTransaction.commit();
        dl_container.closeDrawers()
        return false
    }

    /**
     * 关联NavigationView和DrawerLayout、ToolBar
     */
    open public fun concactNavigationView() {
        //顶部开关
        var actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            dl_container,
            tb_header,
            R.string.menu_customview,
            R.string.menu_camareview
        )
        dl_container.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        //取出灰色默认图标效果
        nv_menu_container.itemIconTintList = null
        //设置点击切换
        nv_menu_container.setNavigationItemSelectedListener(this)
        //整体进行侧滑
        dl_container.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                // 得到contentView 实现侧滑界面出现后主界面向右平移避免侧滑界面遮住主界面
                val content = dl_container.getChildAt(0)
                val offset = drawerView.width * slideOffset
                content.setTranslationX(offset)
            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerOpened(drawerView: View) {
            }

        })
    }
}